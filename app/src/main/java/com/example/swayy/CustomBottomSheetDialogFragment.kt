package com.example.swayy

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.swayy.Adapter.TrendAdapter
import com.example.swayy.Adapter.UserAdapter
import com.example.swayy.model.Items
import com.example.swayy.model.Saveditem
import com.example.swayy.model.User
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home2.view.*
import kotlinx.android.synthetic.main.fragment_private.*
import kotlinx.android.synthetic.main.fragment_saledetail.view.*
import kotlinx.android.synthetic.main.fragment_storedetail.view.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.faidooa
import kotlinx.android.synthetic.main.layout_bottom_sheet.view.*

class CustomBottomSheetDialogFragment: BottomSheetDialogFragment() {
    private var mUser:MutableList<User>? = null
    private var userAdapter: UserAdapter? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var userid:String

    companion object {
        const val TAG = "CustomBottomSheetDialogFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.layout_bottom_sheet,container,false)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        if (pref != null)
        {
            this.userid = pref.getString("userid","none").toString()

        }



//        val userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)
//
//        userRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(p0: DataSnapshot) {
//
//                if (p0.exists())
//                {
//                    val user = p0.getValue<User>(User::class.java)
//
//
//                    val testing = user!!.getUid()
//
//                    val jobsReff = FirebaseDatabase.getInstance().getReference("Users").child(testing)
//                    jobsReff.addChildEventListener(object : ChildEventListener {
//                        override fun onCancelled(error: DatabaseError) {
//                            TODO("Not yet implemented")
//                        }
//
//                        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                            TODO("Not yet implemented")
//                        }
//
//                        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                            mUser?.clear()
//
//
//                                val job = snapshot.getValue(User::class.java)
//                                if (job != null)
//                                {
//                                    mUser?.add(job)
//                                    view.mamayaoaa.text = job!!.getFullname()
//                                }
//                                userAdapter?.notifyDataSetChanged()
//
//
//                        }
//
//
//                        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//
//
//
//                                val job = snapshot.getValue(User::class.java)
//                                if (job != null)
//                                {
//                                    mUser?.add(job)
//                                    view.mamayaoaa.text = job!!.getFullname()
//                                }
//                                userAdapter?.notifyDataSetChanged()
//
//                        }
//
//                        override fun onChildRemoved(snapshot: DataSnapshot) {
//
//                        }
//
//                    })
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })



        val jobsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userid)

        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)

                    Glide.with(context!!)  //2
                        .load(user!!.getImage()).placeholder(R.drawable.twoo) //3
                        .centerCrop() //4
                        .into(view!!.dpyao)
                    view.mamayaoaa.text = user!!.getFullname()
                    view.webmeka.text = user!!.getMobile()
                    view.webmeksa.text = user!!.getTime()

                    val testing = user!!.getUid()

                    val followingRef = firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference.child("Follow").child(it1.toString())
                            .child("Following")
                    }



                    followingRef.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.child(testing).exists())
                            {
                                view?.faizaay?.text = "Following"
                            }
                            else{
                                view?.faizaay?.text = "Follow"
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


                    view?.faizaay?.setOnClickListener {
                        if (view.faizaay?.text.toString() == "Follow")
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
                                                            val notRef = FirebaseDatabase.getInstance().reference.child("Notifications").child(testing)

                                                            val commentsMap = HashMap<String, Any>()
                                                            commentsMap["notification"] = "started following you"
                                                            commentsMap["publisher"] = firebaseUser!!.uid
                                                            commentsMap["image"] = ""

                                                            notRef.push().setValue(commentsMap)
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

                    if (user.getUid() == firebaseUser.uid)
                    {
                        view.faidooa.text = "Edit profile"

                        view.faidooa.setOnClickListener(View.OnClickListener {
                            val pref = context!!.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                            pref.putString("userid",user.getUid())
                            pref.apply()
                            startActivity(Intent(context,ProfileActivity::class.java))
                        })
                    }

                    if (user.getUid() != firebaseUser.uid)
                    {
                        view.faidooa.text = "Message"

                        val wewe = user!!.getUid()

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





        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}