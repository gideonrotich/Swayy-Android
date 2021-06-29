package com.example.swayy.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.bumptech.glide.Glide
import com.example.swayy.*
import com.example.swayy.Adapter.ItemsAdapter
import com.example.swayy.Adapter.NunuaAdapter
import com.example.swayy.R
import com.example.swayy.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.fragment_home2.shimmerFrameLayout
import kotlinx.android.synthetic.main.fragment_home2.view.*
import kotlinx.android.synthetic.main.fragment_notifications2.*
import kotlinx.android.synthetic.main.fragment_saledetail.*
import kotlinx.android.synthetic.main.fragment_saledetail.view.*
import kotlinx.android.synthetic.main.fragment_storedetail.*
import kotlinx.android.synthetic.main.fragment_storedetail.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class storedetailFragment : Fragment() {
    private lateinit var profileId:String
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var lawama:String
    private lateinit var swayy:String
    private lateinit var wamocho:String
    private var itemsAdapter: ItemsAdapter? = null
    private var mItems:MutableList<Items>? = null
    private var nunuaAdapter:NunuaAdapter? = null
    private var mNunua:MutableList<Nunua>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, notificationsFragment()).commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_storedetail, container, false)

        if (FirebaseAuth.getInstance().currentUser != null)

        {
            firebaseUser = FirebaseAuth.getInstance().currentUser!!
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)



            if (pref != null)
            {
                this.profileId = pref.getString("profileId","none").toString()
                this.lawama = pref.getString("lawama","none").toString()
                this.swayy = pref.getString("swayy","none")!!
                this.wamocho = pref.getString("wamocho","none")!!
            }





            AndroidNetworking.initialize(context)
            AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
                .build()
                .getAsObjectList(Items::class.java, object : ParsedRequestListener<List<Items>> {
                    override fun onResponse(items: List<Items>) {
                        shimmerFrameLayout.stopShimmerAnimation()
                        shimmerFrameLayout.visibility = View.GONE


                        val userRef = FirebaseDatabase.getInstance().getReference(lawama).child("Stores").child(swayy)

                        userRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot) {

                                if (p0.exists())
                                {
                                    val user = p0.getValue<Store>(Store::class.java)


                                    val testing = user!!.getUid()

//                    view.aliha.setOnClickListener(View.OnClickListener {
//                        val pref = context!!.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
//                        pref.putString("testing",user!!.getUid())
//
//
//                        pref.apply()
//
//
//                        startActivity(Intent(context,FollowersActivity::class.java))
//                    })

                                    val followingRef = firebaseUser.uid.let { it1 ->
                                        FirebaseDatabase.getInstance().reference.child("Follow").child(it1.toString())
                                            .child("Following")
                                    }


/////////////////////////////////////////////////////////////////////////////
                                    if (testing != firebaseUser.uid)
                                    {
                                        view?.mbeyaz?.visibility = View.GONE
                                    }
                                    else{
                                        view?.mbeyaz?.visibility = View.VISIBLE
                                    }




                                    followingRef.addValueEventListener(object : ValueEventListener{
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            if (snapshot.child(testing).exists())
                                            {
                                                view?.faiza?.text = "Following"
                                            }
                                            else{
                                                view?.faiza?.text = "Follow"
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {

                                        }
                                    })


///////////////////////////////////////////////////////////////////////////////////////////////


                                    val followersRef = FirebaseDatabase.getInstance().reference.child("Follow").child(testing)
                                        .child("Following")


                                    followersRef.addValueEventListener(object : ValueEventListener{
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            if (snapshot.exists())
                                            {
                                                view?.ajedmm?.text = snapshot.childrenCount.toString()
                                            }
                                            else{
                                                view?.ajedmm?.text = "0"
                                            }
                                        }
                                        override fun onCancelled(error: DatabaseError) {

                                        }
                                    })

                                    val followerssRef = FirebaseDatabase.getInstance().reference.child("Follow").child(testing)
                                        .child("Followers")


                                    followerssRef.addValueEventListener(object : ValueEventListener{
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            if (snapshot.exists())
                                            {
                                                view?.ajedm?.text = snapshot.childrenCount.toString()
                                            }
                                            else{
                                                view?.ajedm?.text = "0"
                                            }
                                        }
                                        override fun onCancelled(error: DatabaseError) {

                                        }
                                    })


                                    view?.faiza?.setOnClickListener {
                                        if (view.faiza?.text.toString() == "Follow")
                                        {
                                            firebaseUser.uid.let { it1 ->
                                                FirebaseDatabase.getInstance().reference.child("Follow").child(it1.toString()).child("Following").child(testing)
                                                    .setValue(true).addOnCompleteListener { task ->
                                                        if (task.isSuccessful)
                                                        {

                                                            firebaseUser.uid.let { it1 ->
                                                                FirebaseDatabase.getInstance().reference.child("Follow").child(testing).child("Followers").child(it1.toString())
                                                                    .setValue(true).addOnCompleteListener { task ->
                                                                        if (task.isSuccessful)
                                                                        {

                                                                        }
                                                                    }
                                                            }




                                                        }
                                                    }
                                            }
                                        }
                                        else
                                        {
                                            firebaseUser.uid.let { it1 ->
                                                FirebaseDatabase.getInstance().reference.child("Follow").child(it1.toString()).child("Following").child(testing)
                                                    .removeValue().addOnCompleteListener { task ->
                                                        if (task.isSuccessful)
                                                        {

                                                            firebaseUser.uid.let { it1 ->
                                                                FirebaseDatabase.getInstance().reference.child("Follow").child(testing).child("Followers").child(it1.toString())
                                                                    .removeValue().addOnCompleteListener { task ->
                                                                        if (task.isSuccessful)
                                                                        {

                                                                        }
                                                                    }
                                                            }




                                                        }
                                                    }
                                            }
                                        }
                                    }

                                    val jobsReff = FirebaseDatabase.getInstance().getReference("Duka").child(testing)
                                    jobsReff.addChildEventListener(object : ChildEventListener {
                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }

                                        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                                            TODO("Not yet implemented")
                                        }

                                        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                                            mNunua?.clear()

                                            if (snapshot.exists()){
                                                val job = snapshot.getValue(Nunua::class.java)
                                                if (job != null)
                                                {
                                                    mNunua?.add(job)
                                                    view?.ajed?.text = snapshot.childrenCount.toString()
                                                }
                                                else{
                                                    view?.ajed?.text = "0"
                                                }
                                                nunuaAdapter?.notifyDataSetChanged()
                                            }

                                        }


                                        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {


                                            if (snapshot.exists()){
                                                val job = snapshot.getValue(Nunua::class.java)
                                                if (job != null)
                                                {
                                                    mNunua?.add(job)
                                                    view?.ajed?.text = snapshot.childrenCount.toString()
                                                }
                                                nunuaAdapter?.notifyDataSetChanged()
                                            }
                                            else{
                                                view?.ajed?.text = "0"
                                            }
                                        }

                                        override fun onChildRemoved(snapshot: DataSnapshot) {

                                        }

                                    })

                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }

                    override fun onError(anError: ANError) {
                        shimmerFrameLayout.visibility = View.GONE
                        recycler_view_sasa.visibility = View.GONE
                        Toast.makeText(context, "Check your Internet connection..", Toast.LENGTH_LONG).show()



                    }
                })




            val jobsoneRef = FirebaseDatabase.getInstance().getReference(lawama).child("Stores").child(swayy)

            jobsoneRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
//                if (context != null)
//                {view?.harmo?.text = job!!.getJobtitle()

//                    return
//                }
                    if (p0.exists())
                    {
                        val job = p0.getValue<Store>(Store::class.java)


                        Glide.with(context!!)  //2
                            .load(job!!.getImage()) //3
                            .centerCrop() //4
                            .into(view!!.pichakampuni)
                        view?.mamayao?.text = job!!.getTitle()
                        view?.webme?.text = job!!.getWebsite()
                        view?.webmek?.text = job!!.getLocation()
                        view?.websasa?.text = job!!.getDescription()

                        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                        val currentDate = sdf.format(Date())

                        view?.webmeks?.text = job!!.getTime()

                        view.mbeya.setOnClickListener {

//                        val share = Intent.createChooser(Intent().apply {
//                            action = Intent.ACTION_SEND
//                            putExtra(Intent.EXTRA_TEXT, "https://swayy/store/"+job.getTime())
//
//                            // (Optional) Here we're setting the title of the content
////                            putExtra(Intent.EXTRA_TITLE, "Check out  " +job.getTitle() + "shop in the swayy app")
//
//                            // (Optional) Here we're passing a content URI to an image to be displayed
////                            data = contentUri
//                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//                        }, null)
//                        startActivity(share)

                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Check out this "+job.getTitle() + " store in the swayy app \n https://play.google.com/store/apps/details?id=cn.swayy")
//                            putExtra(Intent.EXTRA_TITLE, "Check out  " +job.getTitle() + "shop in the swayy app")
                                type = "text/plain"



                            }

                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(shareIntent)


                        }






                        view?.mbeyaz?.setOnClickListener {
                            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                            pref?.putString("ndan",job.getCategory())
                            pref?.putString("mat",job.getUid())
                            pref?.apply()

                            startActivity(Intent(context,settingsActivity::class.java))
                        }



                        if (job.getUid() == firebaseUser.uid)
                        {
                            view.faidoo.text = "Add Post"

                            view.faidoo.setOnClickListener(View.OnClickListener {
                                val pref = context!!.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                                pref.putString("storeid",job.getStoreid())


                                pref.apply()


                                startActivity(Intent(context,nunuaActivity::class.java))
                            })
                        }
                        if (job.getUid() != firebaseUser.uid)
                        {
                            view.faidoo.text = "Message Shop"

                            val wewe = job!!.getUid()

                            val jobsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(wewe)

                            jobsRef.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(p0: DataSnapshot) {

                                    if (p0.exists())
                                    {
                                        val user = p0.getValue<User>(User::class.java)


                                        faidoo.setOnClickListener {
                                            val packageManager = context!!.packageManager
                                            val toNumber:String = "254"+user!!.getMobile()
                                            val mimi = job.getTitle()
                                            val indi = user!!.getFullname()
                                            val text:String = "Hello, am "+indi +" and am interested in your " +mimi +" that you posted in the Swayy app"
                                            val sendIntent = Intent(Intent.ACTION_VIEW)
                                            sendIntent.data = Uri.parse("http://api.whatsapp.com/send?phone="+toNumber + "&text="+text)
                                            startActivity(sendIntent)

                                        }







                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })
                        }




//

//                    val userRef = FirebaseDatabase.getInstance().getReference().child("Stores").child(swayy)
//
//                    userRef.addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(p0: DataSnapshot) {
//
//                            if (p0.exists())
//                            {
//                                val user = p0.getValue<Store>(Store::class.java)
//
//                                val mwewe = user!!.getUid()
//
//                    val jobsReff = FirebaseDatabase.getInstance().getReference("Babies and kids").child("Babies and kids accessories").child(mwewe)
//                                jobsReff.addChildEventListener(object : ChildEventListener {
//                                    override fun onCancelled(error: DatabaseError) {
//                                        TODO("Not yet implemented")
//                                    }
//
//                                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                                        TODO("Not yet implemented")
//                                    }
//
//                                    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                                        mItems?.clear()
//
//                                        if (snapshot.exists()){
//                                            val job = snapshot.getValue(Items::class.java)
//                                            if (job != null)
//                                            {
//                                                mItems?.add(job)
//                                            }
//                                            itemsAdapter?.notifyDataSetChanged()
//                                        }
//
//                                    }
//
//
//                                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//
//
//                                        if (snapshot.exists()){
//                                            val job = snapshot.getValue(Items::class.java)
//                                            if (job != null)
//                                            {
//                                                mItems?.add(job)
//                                            }
//                                            itemsAdapter?.notifyDataSetChanged()
//                                        }
//                                    }
//
//                                    override fun onChildRemoved(snapshot: DataSnapshot) {
//
//                                    }
//
//                                })
//                            }
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            TODO("Not yet implemented")
//                        }
//                    })





                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

            var recyclerviewsasa: RecyclerView? = null

            var num = 2
            recyclerviewsasa = view.findViewById(R.id.recycler_view_sasa)
            recyclerviewsasa?.setHasFixedSize(true)
            recyclerviewsasa.itemAnimator = DefaultItemAnimator()
            val linearLayout = GridLayoutManager(context,num)
            linearLayout.reverseLayout = true
            linearLayout.orientation = LinearLayoutManager.VERTICAL
            recyclerviewsasa?.layoutManager = linearLayout
            mNunua = ArrayList()
            nunuaAdapter = context?.let { NunuaAdapter(it,mNunua as ArrayList<Nunua>,true) }
            recyclerviewsasa?.adapter = nunuaAdapter
            recyclerviewsasa?.visibility = View.VISIBLE


        }






        return view
    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser == null)
        {

            majira.visibility = View.GONE
            minanyangol.visibility = View.VISIBLE

            bahatii.setOnClickListener {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }
            textView288.setOnClickListener {
                val modalbottomSheetFragment = CustomSignupFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }
            }

        }

    }






    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }
    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }


}