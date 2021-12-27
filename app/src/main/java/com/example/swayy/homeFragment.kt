package com.example.swayy

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.bumptech.glide.Glide
import com.example.swayy.Adapter.ItemsAdapter
import com.example.swayy.Adapter.NotificationAdapter
import com.example.swayy.Adapter.StoreAdapter
import com.example.swayy.Adapter.TrendAdapter
import com.example.swayy.Search.KabaFragment
import com.example.swayy.Search.SearchFragment
import com.example.swayy.Search.tafutaFragment
import com.example.swayy.groups.*
import com.example.swayy.message.LatestMessagesActivity
import com.example.swayy.model.*
import com.example.swayy.settings.noteActivity
import com.example.swayy.stores.storeHomeActivity
import com.example.swayy.stores.storesActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_animals.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_signin.view.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.view.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.fragment_home2.shimmerFrameLayout
import kotlinx.android.synthetic.main.fragment_home2.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import kotlin.system.exitProcess


class homeFragment : Fragment() {
    private var itemsAdapter: ItemsAdapter? = null
    private var trendAdapter:TrendAdapter? = null
    private var mItems:MutableList<Items>? = null
    private var mStore:MutableList<Store>? = null
    private var storeAdapter:StoreAdapter? = null
    private var mTrend:MutableList<Trend>? = null
    private var mUser:MutableList<User>? = null
    private var notificationAdapter: NotificationAdapter? = null
    private var mNotification:MutableList<Notification>? = null
    private lateinit var firebaseUser: FirebaseUser
    lateinit var mAdView : AdView
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val eBuilder = AlertDialog.Builder(context)
                eBuilder.setCancelable(false)
                eBuilder.setTitle("Exit")
                eBuilder.setIcon(R.drawable.ic_baseline_warning_24)
                eBuilder.setMessage("Are you sure you want to Exit?")
                eBuilder.setPositiveButton("Yes")


                {
                    Dialog,which->
                    exitProcess(0)
                }
                eBuilder.setNegativeButton("No")
                {
                    Dialog,which ->


                }
                val createBuild  = eBuilder.create()
                createBuild.show()

            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home2, container, false)
//       if (FirebaseAuth.getInstance().currentUser == null)
//        {
//            val modalbottomSheetFragment = CustomLoginFragment()
//            activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }
//
//        }else {
//            firebaseUser = FirebaseAuth.getInstance().currentUser!!
//        }

        if (FirebaseAuth.getInstance().currentUser != null)
        {
            firebaseUser = FirebaseAuth.getInstance().currentUser!!
            retrieveUser()

            val followerssRef = FirebaseDatabase.getInstance().reference.child("Notifications").child(firebaseUser.uid)


            followerssRef.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists())
                    {
                        view?.card_badge?.visibility = View.VISIBLE
                        view?.neyo?.text = snapshot.childrenCount.toString()


                    }
                    else{
//                        view?.ajedma?.text = "0"
                        view.opttomo.setImageResource(R.drawable.ic_notifications)
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })

        }



        setupAPICall()

        view.opttomo.setOnClickListener {
        if (FirebaseAuth.getInstance().currentUser == null)
        {
            val modalbottomSheetFragment = CustomLoginFragment()
            activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

        }
        else{
            firebaseUser = FirebaseAuth.getInstance().currentUser!!
                startActivity(Intent(context, noteActivity::class.java))

        }
        }

        view.one.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {

                if (mInterstitialAd != null) {
                    // Show the ad
                    mInterstitialAd!!.show(context as Activity)
                    mInterstitialAd!!.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                firebaseUser = FirebaseAuth.getInstance().currentUser!!
                                val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                                pref?.putString("profileId",view.tanonane.text.toString())
                                pref?.apply()
                                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                                    .replace(R.id.frame_layout,chakulaFragment()).commit()
                                loadInterstitial()
                            }

                            override fun onAdShowedFullScreenContent() {
                                loadInterstitial()
                                Log.d("TAG", "The ad was shown.")
                            }
                        }
                } else {
                    firebaseUser = FirebaseAuth.getInstance().currentUser!!
                    val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                    pref?.putString("profileId",view.tanonane.text.toString())
                    pref?.apply()
                    (context as FragmentActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout,chakulaFragment()).commit()
                }
            }

        }
        view.two.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                pref?.putString("profileId",view.gs.text.toString())
                pref?.apply()
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,wanyamaFragment()).commit()
            }

        }

        view.three.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                pref?.putString("profileId",view.credo.text.toString())
                pref?.apply()
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,sisiFragment()).commit()
            }

        }

        view.four.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                pref?.putString("profileId",view.tap.text.toString())
                pref?.apply()
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,mzFragment()).commit()
            }

        }



        view.opttom.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                pref?.putString("userid",firebaseUser.uid.toString())
                pref?.apply()


                val modalbottomSheetFragment = CustomBottomSheetDialogFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }
            }

        }
        view.five.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                pref?.putString("profileId",view.kimp.text.toString())
                pref?.apply()
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,gariFragment()).commit()
            }

        }

        view.six.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                pref?.putString("profileId",view.dunda.text.toString())
                pref?.apply()
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,remboFragment()).commit()
            }

        }

        view.seven.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                pref?.putString("profileId",view.checketua.text.toString())
                pref?.apply()
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,undaFragment()).commit()
            }

        }

        view.eight.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                pref?.putString("profileId",view.nini.text.toString())
                pref?.apply()
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,simuFragment()).commit()
            }

        }
        view.tis.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                pref?.putString("profileId",view.kangu.text.toString())
                pref?.apply()
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,nyumbaFragment()).commit()
            }

        }

//        view.picphones.setOnClickListener {
//            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
//            pref?.putString("profileId",view.mwenyewe.text.toString())
//            pref?.apply()
//            (context as FragmentActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.frame_layout,comFragment()).commit()
//        }
//        view.picphonebody.setOnClickListener {
//            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
//            pref?.putString("profileId",view.mwenyewe.text.toString())
//            pref?.apply()
//            (context as FragmentActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.frame_layout, mzFragment()).commit()
//        }


        view.accnada.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                firebaseUser = FirebaseAuth.getInstance().currentUser!!
                val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                pref?.apply()
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,SearchFragment()).commit()
            }

        }

        var recyclerviewmimi: RecyclerView? = null
        var recyclerviewtrend:RecyclerView? = null
        var recyclerviewstore:RecyclerView? = null

        var num = 2
        recyclerviewmimi = view.findViewById(R.id.recycler_view_home_t)
        recyclerviewmimi?.setHasFixedSize(true)
        recyclerviewmimi.itemAnimator = DefaultItemAnimator()
        val linearLayout = GridLayoutManager(context,num)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        recyclerviewmimi?.layoutManager = linearLayout
        mItems = ArrayList()
        itemsAdapter = context?.let { ItemsAdapter(it,mItems as ArrayList<Items>,true) }
        recyclerviewmimi?.adapter = itemsAdapter
        recyclerviewmimi?.visibility = View.VISIBLE


        recyclerviewtrend = view.findViewById(R.id.recycler_view_trend)
        recyclerviewtrend?.setHasFixedSize(true)
        recyclerviewtrend.itemAnimator = DefaultItemAnimator()
        val linearLayoutt = LinearLayoutManager(context)
        linearLayoutt.reverseLayout = true
        linearLayoutt.stackFromEnd = true
        linearLayoutt.orientation = LinearLayoutManager.HORIZONTAL
        recyclerviewtrend?.layoutManager = linearLayoutt
        mTrend = ArrayList()
        trendAdapter = context?.let { TrendAdapter(it,mTrend as ArrayList<Trend>,true) }
        recyclerviewtrend?.adapter = trendAdapter
        recyclerviewtrend?.visibility = View.VISIBLE


        recyclerviewstore = view.findViewById(R.id.recycler_view_duka)
        recyclerviewstore?.setHasFixedSize(true)
        recyclerviewstore.itemAnimator = DefaultItemAnimator()
        val linearLayoutta = LinearLayoutManager(context)
        linearLayoutta.reverseLayout = true
        linearLayoutta.stackFromEnd = true
        linearLayoutta.orientation = LinearLayoutManager.HORIZONTAL
        recyclerviewstore?.layoutManager = linearLayoutta
        mStore = ArrayList()
        storeAdapter = context?.let { StoreAdapter(it,mStore as ArrayList<Store>,true) }
        recyclerviewstore?.adapter = storeAdapter
        recyclerviewstore?.visibility = View.VISIBLE




        val jobsRef = FirebaseDatabase.getInstance().getReference("Babies and kids").child("Babies and kids accessories")
        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                mItems?.clear()

                for (snapshot in p0.children)
                {
                    val job = snapshot.getValue(Items::class.java)
                    val mew = job!!.getUid()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        //Ads
        loadBanners()


        return view


    }

    private fun setupAPICall() {
        AndroidNetworking.initialize(context)
        AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
            .build()
            .getAsObjectList(Items::class.java, object : ParsedRequestListener<List<Items>> {
                override fun onResponse(items: List<Items>) {
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
//                    view.recycler_view_post.visibility = View.VISIBLE


                    retrieveFeed()
                    retrieveTrend()
                    retrieveStore()
//


                    view?.recycler_view_home_t?.visibility = View.VISIBLE
                    view?.trendtext?.visibility = View.VISIBLE
                    view?.trendtextmojo?.visibility = View.VISIBLE
                    view?.trendtextrec?.visibility = View.VISIBLE
                    view?.opttomsn?.visibility = View.VISIBLE
                    view?.opttomn?.visibility = View.VISIBLE

                    view?.see_all?.visibility = View.VISIBLE
                    view?.see_allmojo?.visibility = View.VISIBLE
                    view?.see_allll?.visibility = View.VISIBLE


                }

                override fun onError(anError: ANError) {
                    shimmerFrameLayout.visibility = View.GONE
                    recycler_view_home_t.visibility = View.GONE
                    naneanimationn.visibility = View.VISIBLE



                }
            })
    }

    private fun retrieveUser() {
        val jobsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)

        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)

                    Glide.with(context!!)  //2
                        .load(user!!.getImage()).placeholder(R.drawable.twoo) //3
                        .centerCrop() //4
                        .into(view!!.opttom)


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun loadInterstitial() {
        val adRequest = AdRequest.Builder().build()
        activity?.let {
            InterstitialAd.load(
                it,
                resources.getString(R.string.interstitial),
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        super.onAdLoaded(interstitialAd)
                        mInterstitialAd = interstitialAd
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        super.onAdFailedToLoad(loadAdError)
                        mInterstitialAd = null
                    }
                })
        }
    }


    private fun retrieveTrend() {
        val jobsReff = FirebaseDatabase.getInstance().getReference("Babies and kids").child("Babies and kids accessories")
        jobsReff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                mTrend?.clear()

                for (snapshot in p0.children)
                {
                    val job = snapshot.getValue(Trend::class.java)
                    if (job != null)
                    {
                        mTrend?.add(job)
                    }
                    trendAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun retrieveFeed() {
        val jobsRef = FirebaseDatabase.getInstance().getReference("Commercial equipment and tools").child("Printing equipment")
        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                mItems?.clear()

                for (snapshot in p0.children)
                {
                    val job = snapshot.getValue(Items::class.java)
                    if (job != null)
                    {
                        mItems?.add(job)
                    }
                    itemsAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun retrieveStore() {
        val jobsRef = FirebaseDatabase.getInstance().getReference("Accommodation and travel agencies").child("Stores")
        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                mStore?.clear()

                for (snapshot in p0.children)
                {
                    val job = snapshot.getValue(Store::class.java)
                    if (job != null)
                    {
                        mStore?.add(job)
                    }
                    storeAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }
    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBanners()
        loadInterstitial()
    }

    fun loadBanners(){
        val adRequest = AdRequest.Builder().build()
        view?.adView?.loadAd(adRequest)
    }






}