package com.example.swayy

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.swayy.Adapter.*
import com.example.swayy.groups.sisiFragment
import com.example.swayy.model.*
import com.example.swayy.stores.holdActivity
import com.example.swayy.stores.storesActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.fragment_home2.recycler_view_duka
import kotlinx.android.synthetic.main.fragment_home2.shimmerFrameLayout
import kotlinx.android.synthetic.main.fragment_notifications2.*
import kotlinx.android.synthetic.main.fragment_notifications2.naneanimation
import kotlinx.android.synthetic.main.fragment_notifications2.view.*


class notificationsFragment : Fragment() {
    private var itemsAdapter: ItemsAdapter? = null
    private var trendAdapter: TrendAdapter? = null
    private var homeAdapter: HomeAdapter? = null
    private var mItems:MutableList<Items>? = null
    private var mStore:MutableList<Store>? = null
    private var mHome:MutableList<Home>? = null
    private var storeAdapter: StoreAdapter? = null
    private var travelAdapter: TravelAdapter? = null
    private var fashionAdapter: FashionAdapter? = null
    private var carAdapter: CarAdapter? = null
    private var provideAdapter: ProvideAdapter? = null
    private var phoneAdapter: PhoneAdapter? = null
    private var generalAdapter: GeneralAdapter? = null
    private var mTravel:MutableList<Travel>? = null
    private var mFashion:MutableList<Fashion>? = null
    private var mTrend:MutableList<Trend>? = null
    private var mCar:MutableList<Car>? = null
    private var mProvide:MutableList<Provide>? = null
    private var mPhone:MutableList<Phone>? = null
    private var mGeneral:MutableList<General>? = null
    private var mUser:MutableList<User>? = null
    private lateinit var firebaseUser: FirebaseUser



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        DatabaseUtil.getDatabase()

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, homeFragment()).commit()
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_notifications2, container, false)


        view.createshop.setOnClickListener {
            startActivity(Intent(context, holdActivity::class.java))
        }

//        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        var recyclerviewstore: RecyclerView? = null
        recyclerviewstore = view.findViewById(R.id.recycler_view_dukam)
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


        var recyclerviewhome: RecyclerView? = null
        recyclerviewhome = view.findViewById(R.id.recycler_view_dukamone)
        recyclerviewhome?.setHasFixedSize(true)
        recyclerviewhome.itemAnimator = DefaultItemAnimator()
        val linearLayouttahome = LinearLayoutManager(context)
        linearLayouttahome.reverseLayout = true
        linearLayouttahome.stackFromEnd = true
        linearLayouttahome.orientation = LinearLayoutManager.HORIZONTAL
        recyclerviewhome?.layoutManager = linearLayouttahome
        mHome = ArrayList()
        homeAdapter = context?.let { HomeAdapter(it,mHome as ArrayList<Home>,true) }
        recyclerviewhome?.adapter = homeAdapter
        recyclerviewhome?.visibility = View.VISIBLE


        var recyclerviewtravel: RecyclerView? = null
        recyclerviewtravel = view.findViewById(R.id.recycler_view_dukatravel)
        recyclerviewtravel?.setHasFixedSize(true)
        recyclerviewtravel.itemAnimator = DefaultItemAnimator()
        val linearLayouttatravel = LinearLayoutManager(context)
        linearLayouttatravel.reverseLayout = true
        linearLayouttatravel.stackFromEnd = true
        linearLayouttatravel.orientation = LinearLayoutManager.HORIZONTAL
        recyclerviewtravel?.layoutManager = linearLayouttatravel
        mTravel = ArrayList()
        travelAdapter = context?.let { TravelAdapter(it,mTravel as ArrayList<Travel>,true) }
        recyclerviewtravel?.adapter = travelAdapter
        recyclerviewtravel?.visibility = View.VISIBLE


        var recyclerviewprovide: RecyclerView? = null
        recyclerviewprovide = view.findViewById(R.id.recycler_view_dukaprovide)
        recyclerviewprovide?.setHasFixedSize(true)
        recyclerviewprovide.itemAnimator = DefaultItemAnimator()
        val linearLayouttprovide = LinearLayoutManager(context)
        linearLayouttprovide.reverseLayout = true
        linearLayouttprovide.stackFromEnd = true
        linearLayouttprovide.orientation = LinearLayoutManager.HORIZONTAL
        recyclerviewprovide?.layoutManager = linearLayouttprovide
        mProvide = ArrayList()
        provideAdapter = context?.let { ProvideAdapter(it,mProvide as ArrayList<Provide>,true) }
        recyclerviewprovide?.adapter = provideAdapter
        recyclerviewprovide?.visibility = View.VISIBLE

        var recyclerviewcar: RecyclerView? = null
        recyclerviewcar = view.findViewById(R.id.recycler_view_dukacar)
        recyclerviewcar?.setHasFixedSize(true)
        recyclerviewcar.itemAnimator = DefaultItemAnimator()
        val linearLayouttcar = LinearLayoutManager(context)
        linearLayouttcar.reverseLayout = true
        linearLayouttcar.stackFromEnd = true
        linearLayouttcar.orientation = LinearLayoutManager.HORIZONTAL
        recyclerviewcar?.layoutManager = linearLayouttcar
        mCar = ArrayList()
        carAdapter = context?.let { CarAdapter(it,mCar as ArrayList<Car>,true) }
        recyclerviewcar?.adapter = carAdapter
        recyclerviewcar?.visibility = View.VISIBLE

        var recyclerviewphone: RecyclerView? = null
        recyclerviewphone = view.findViewById(R.id.recycler_view_dukaphone)
        recyclerviewphone?.setHasFixedSize(true)
        recyclerviewphone.itemAnimator = DefaultItemAnimator()
        val linearLayouttphone = LinearLayoutManager(context)
        linearLayouttphone.reverseLayout = true
        linearLayouttphone.stackFromEnd = true
        linearLayouttphone.orientation = LinearLayoutManager.HORIZONTAL
        recyclerviewphone?.layoutManager = linearLayouttphone
        mPhone = ArrayList()
        phoneAdapter = context?.let { PhoneAdapter(it,mPhone as ArrayList<Phone>,true) }
        recyclerviewphone?.adapter = phoneAdapter
        recyclerviewphone?.visibility = View.VISIBLE

        var recyclerviewfashion: RecyclerView? = null
        recyclerviewfashion = view.findViewById(R.id.recycler_view_dukafashion)
        recyclerviewfashion?.setHasFixedSize(true)
        recyclerviewfashion.itemAnimator = DefaultItemAnimator()
        val linearLayouttfashion = LinearLayoutManager(context)
        linearLayouttfashion.reverseLayout = true
        linearLayouttfashion.stackFromEnd = true
        linearLayouttfashion.orientation = LinearLayoutManager.HORIZONTAL
        recyclerviewfashion?.layoutManager = linearLayouttfashion
        mFashion = ArrayList()
        fashionAdapter = context?.let { FashionAdapter(it,mFashion as ArrayList<Fashion>,true) }
        recyclerviewfashion?.adapter = fashionAdapter
        recyclerviewfashion?.visibility = View.VISIBLE


        val num = 2
        var recyclerviewgeneral: RecyclerView? = null
        recyclerviewgeneral = view.findViewById(R.id.recycler_view_dukageneral)
        recyclerviewgeneral?.setHasFixedSize(true)
        recyclerviewgeneral.itemAnimator = DefaultItemAnimator()
        val linearLayouttgeneral = GridLayoutManager(context,num)
        linearLayouttgeneral.reverseLayout = true
        linearLayouttgeneral.orientation = LinearLayoutManager.VERTICAL
        recyclerviewgeneral?.layoutManager = linearLayouttgeneral
        mGeneral = ArrayList()
        generalAdapter = context?.let { GeneralAdapter(it,mGeneral as ArrayList<General>,true) }
        recyclerviewgeneral?.adapter = generalAdapter
        recyclerviewgeneral?.visibility = View.VISIBLE

        setupAPICall()
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

                    view?.molo?.visibility = View.VISIBLE
                    view?.moloone?.visibility = View.VISIBLE
                    view?.molotravel?.visibility = View.VISIBLE
                    view?.moloprovide?.visibility = View.VISIBLE
                    view?.molocar?.visibility = View.VISIBLE
                    view?.molophone?.visibility = View.VISIBLE
                    view?.molofashion?.visibility = View.VISIBLE
                    view?.mologeneral?.visibility = View.VISIBLE
//                    FirebaseDatabase.getInstance().setPersistenceEnabled(true)
                    val jobsRef = FirebaseDatabase.getInstance().getReference("Electronics stores").child("Stores")
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


                    val jobsRefhome = FirebaseDatabase.getInstance().getReference("Home and living stores").child("Stores")
                    jobsRefhome.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {

                            mHome?.clear()

                            for (snapshot in p0.children)
                            {
                                val jobb = snapshot.getValue(Home::class.java)
                                if (jobb != null)
                                {
                                    mHome?.add(jobb)
                                }
                                homeAdapter?.notifyDataSetChanged()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })


                    val jobsReftravel = FirebaseDatabase.getInstance().getReference("Accommodation and travel agencies").child("Stores")
                    jobsReftravel.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {

                            mTravel?.clear()

                            for (snapshot in p0.children)
                            {
                                val jobb = snapshot.getValue(Travel::class.java)
                                if (jobb != null)
                                {
                                    mTravel?.add(jobb)
                                }
                                travelAdapter?.notifyDataSetChanged()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                    val jobsRefprovide = FirebaseDatabase.getInstance().getReference("Service providers").child("Stores")
                    jobsRefprovide.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {

                            mProvide?.clear()

                            for (snapshot in p0.children)
                            {
                                val jobb = snapshot.getValue(Provide::class.java)
                                if (jobb != null)
                                {
                                    mProvide?.add(jobb)
                                }
                                provideAdapter?.notifyDataSetChanged()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                    val jobsRefcar = FirebaseDatabase.getInstance().getReference("Vehicle dealers").child("Stores")
                    jobsRefcar.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {

                            mCar?.clear()

                            for (snapshot in p0.children)
                            {
                                val jobb = snapshot.getValue(Car::class.java)
                                if (jobb != null)
                                {
                                    mCar?.add(jobb)
                                }
                                carAdapter?.notifyDataSetChanged()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })

                    val jobsRefphone = FirebaseDatabase.getInstance().getReference("Fashion stores").child("Stores")
                    jobsRefphone.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {

                            mFashion?.clear()

                            for (snapshot in p0.children)
                            {
                                val jobb = snapshot.getValue(Fashion::class.java)
                                if (jobb != null)
                                {
                                    mFashion?.add(jobb)
                                }
                                fashionAdapter?.notifyDataSetChanged()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })


                    val jobsRefgeneral = FirebaseDatabase.getInstance().getReference("Other Stores").child("Stores")
                    jobsRefgeneral.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {

                            mGeneral?.clear()

                            for (snapshot in p0.children)
                            {
                                val jobb = snapshot.getValue(General::class.java)
                                if (jobb != null)
                                {
                                    mGeneral?.add(jobb)
                                }
                                generalAdapter?.notifyDataSetChanged()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                    val jobsRefp = FirebaseDatabase.getInstance().getReference("Mobile phone stores").child("Stores")
                    jobsRefp.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {

                            mPhone?.clear()

                            for (snapshot in p0.children)
                            {
                                val jobb = snapshot.getValue(Phone::class.java)
                                if (jobb != null)
                                {
                                    mPhone?.add(jobb)
                                }
                                phoneAdapter?.notifyDataSetChanged()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })



                }

                override fun onError(anError: ANError) {
                    shimmerFrameLayout.visibility = View.GONE
                    recycler_view_dukam.visibility = View.GONE
                    naneanimation.visibility = View.VISIBLE



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

    class DatabaseUtil {
        companion object {
            private val firebaseDatabase: FirebaseDatabase  = FirebaseDatabase.getInstance()
            init {
                firebaseDatabase.setPersistenceEnabled(true)
            }
            fun getDatabase() : FirebaseDatabase {
                return firebaseDatabase
            }
        }
    }


}