package com.example.swayy

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swayy.Adapter.ItemsAdapter
import com.example.swayy.Adapter.NunuaAdapter
import com.example.swayy.Adapter.UserAdapter
import com.example.swayy.model.Items
import com.example.swayy.model.Nunua
import com.example.swayy.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_private.*
import kotlinx.android.synthetic.main.fragment_private.view.*
import kotlinx.android.synthetic.main.fragment_storedetail.view.*


class privateFragment : Fragment() {
    private lateinit var firebaseUser: FirebaseUser
    private var itemsAdapter: ItemsAdapter? = null
    private var mItems:MutableList<Items>? = null
    private var userAdapter: UserAdapter? = null
    private var mUser:MutableList<User>? = null
    private lateinit var userid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val view =  inflater.inflate(R.layout.fragment_private, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        if (pref != null)
        {
            this.userid = pref.getString("userid","none").toString()

        }


        val jobsoneRef = FirebaseDatabase.getInstance().getReference("Users").child(userid)

        jobsoneRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
//                if (context != null)
//                {view?.harmo?.text = job!!.getJobtitle()

//                    return
//                }
                if (p0.exists())
                {
                    val job = p0.getValue<User>(User::class.java)

                    val testing = job!!.getUid()

                    val jobsReff = FirebaseDatabase.getInstance().getReference("Duka").child(testing)
                    jobsReff.addChildEventListener(object : ChildEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                            TODO("Not yet implemented")
                        }

                        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                            mItems?.clear()

                            if (snapshot.exists()){
                                val job = snapshot.getValue(Items::class.java)
                                if (job != null)
                                {
                                    mItems?.add(job)
                                    view?.ajedo?.text = snapshot.childrenCount.toString()
                                }
                                else{
                                    view?.ajedo?.text = "0"
                                }
                                itemsAdapter?.notifyDataSetChanged()
                            }

                        }


                        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {


                            if (snapshot.exists()){
                                val job = snapshot.getValue(Items::class.java)
                                if (job != null)
                                {
                                    mItems?.add(job)
                                    view?.ajedo?.text = snapshot.childrenCount.toString()
                                }
                                itemsAdapter?.notifyDataSetChanged()
                            }
                            else{
                                view?.ajedo?.text = "0"
                            }
                        }

                        override fun onChildRemoved(snapshot: DataSnapshot) {

                        }

                    })

                    val followingRef = firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference.child("Follow").child(it1.toString())
                            .child("Following")
                    }

                    followingRef.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.child(testing).exists())
                            {
                                view?.faizaa?.text = "Following"
                            }
                            else{
                                view?.faizaa?.text = "Follow"
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })


                    val followersRef = FirebaseDatabase.getInstance().reference.child("Follow").child(testing)
                        .child("Following")


                    followersRef.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists())
                            {
                                view?.ajedmma?.text = snapshot.childrenCount.toString()
                            }
                            else{
                                view?.ajedmma?.text = "0"
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
                                view?.ajedma?.text = snapshot.childrenCount.toString()
                            }
                            else{
                                view?.ajedma?.text = "0"
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {

                        }
                    })


                    view?.faizaa?.setOnClickListener {
                        if (view.faizaa?.text.toString() == "Follow")
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




///////////////////////////////////////////////////////////////////////////////////////

                    Glide.with(context!!)  //2
                        .load(job!!.getImage()) //3
                        .centerCrop() //4
                        .into(view!!.dpyao)
                    view?.mamayaoa?.text = job!!.getFullname()
//                    view?.webme?.text = job!!.getWebsite()
                    view?.webmeka?.text = job!!.getMobile()
//                    view?.websasa?.text = job!!.getDescription()
//                    view?.webmeks?.text = job!!.getTime()

                    view.mbeyar.setOnClickListener {

                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Check out  my profile in the swayy app \n https://play.google.com/store/apps/details?id=cn.swayy")
//                            putExtra(Intent.EXTRA_TITLE, "Check out  " +job.getTitle() + "shop in the swayy app")
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)


                    }


                    if (job.getUid() == firebaseUser.uid)
                    {
                        view.faidooa.text = "Edit profile"

                        view.faidooa.setOnClickListener(View.OnClickListener {
                            val pref = context!!.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                            pref.putString("userid",job.getUid())
                            pref.apply()
                            startActivity(Intent(context,ProfileActivity::class.java))
                        })
                    }
                    if (job.getUid() != firebaseUser.uid)
                    {
                        view.faidooa.text = "Message"

                        val wewe = job!!.getUid()

                        val jobsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(wewe)

                        jobsRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot) {

                                if (p0.exists())
                                {
                                    val user = p0.getValue<User>(User::class.java)


                                    faidooa.setOnClickListener {
                                        val packageManager = context!!.packageManager
                                        val toNumber:String = "254"+user!!.getMobile()
                                        val indi = user!!.getFullname()
                                        val text:String = "Hello, am "+indi +" and i would like to talk to you"
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
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        var recyclerviewsasap: RecyclerView? = null

        var num = 2
        recyclerviewsasap = view.findViewById(R.id.recycler_view_sasap)
        recyclerviewsasap?.setHasFixedSize(true)
        recyclerviewsasap.itemAnimator = DefaultItemAnimator()
        val linearLayout = GridLayoutManager(context,num)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        recyclerviewsasap?.layoutManager = linearLayout
        mItems = ArrayList()
        itemsAdapter = context?.let { ItemsAdapter(it,mItems as ArrayList<Items>,true) }
        recyclerviewsasap?.adapter = itemsAdapter
        recyclerviewsasap?.visibility = View.VISIBLE


        return view
    }


}