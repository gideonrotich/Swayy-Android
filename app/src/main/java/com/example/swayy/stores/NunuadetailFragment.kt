package com.example.swayy.stores

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
import com.bumptech.glide.Glide
import com.example.swayy.*
import com.example.swayy.Adapter.ItemsAdapter
import com.example.swayy.Adapter.NunuaAdapter
import com.example.swayy.categories.saledetailFragment
import com.example.swayy.message.ChatLogActivity
import com.example.swayy.message.UserItem
import com.example.swayy.model.Items
import com.example.swayy.model.Nunua
import com.example.swayy.model.User
import com.example.swayy.model.Userr
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_nunuadetail.*
import kotlinx.android.synthetic.main.fragment_nunuadetail.view.*
import kotlinx.android.synthetic.main.fragment_saledetail.*
import kotlinx.android.synthetic.main.fragment_saledetail.view.*

class NunuadetailFragment : Fragment() {
    private lateinit var profileId:String
    private lateinit var lawama:String
    private var nunuaAdapter: NunuaAdapter? = null
    private var mNunua:MutableList<Nunua>? = null
    private lateinit var firebaseUser: FirebaseUser

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
        val view =  inflater.inflate(R.layout.fragment_nunuadetail, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        view.accnadel.setOnClickListener {
            startActivity(Intent(context, ItemsaleActivity::class.java))
        }


        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()
            this.lawama = pref.getString("lawama","none").toString()
        }

        val jobsoneRef = FirebaseDatabase.getInstance().getReference().child("Duka").child(lawama).child(profileId)

        jobsoneRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
//                if (context != null)
//                {view?.harmo?.text = job!!.getJobtitle()

//                    return
//                }
                if (p0.exists())
                {
                    val job = p0.getValue<Nunua>(Nunua::class.java)
                    view.rosal.text = job!!.getTitle()

                    view.pichalikel.setOnClickListener {
                        if (view.pichalikel.tag == "Like")
                        {
                            FirebaseDatabase.getInstance().reference
                                .child("Likes")
                                .child(job!!.getPostid())
                                .child(firebaseUser!!.uid)
                                .setValue(true)
                        }
                        else{
                            FirebaseDatabase.getInstance().reference
                                .child("Likes")
                                .child(job!!.getPostid())
                                .child(firebaseUser!!.uid)
                                .removeValue()
                            val intent = Intent(context, MainActivity::class.java)
                            context!!.startActivity(intent)
                        }
                    }

                    val firebaseUser = FirebaseAuth.getInstance().currentUser

                    val LikesRef = FirebaseDatabase.getInstance().reference
                        .child("Likes").child(job!!.getPostid())

                    LikesRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {
                            if (p0.child(firebaseUser!!.uid).exists())
                            {
                                view.pichalikel.setImageResource(R.drawable.ic_thumb_up)
                                view.pichalikel.tag = "Liked"
                            }
                            else
                            {
                                view.pichalikel.setImageResource(R.drawable.ic_likeone)
                                view.pichalikel.tag = "Like"
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })

                    val LikessRef = FirebaseDatabase.getInstance().reference
                        .child("Likes").child(job.getPostid())

                    LikessRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {
                            if (p0.exists())
                            {
                                view.likesl.text = p0.childrenCount.toString() + ""
                            }

                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })

                    val wewe = job!!.getUid()

                    val jobsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(wewe)

                    jobsRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {

                            if (p0.exists())
                            {
                                val user = p0.getValue<User>(User::class.java)

                                Picasso.get().load(user!!.getImage()).into(proml)

                                proml.setOnClickListener {
                                    val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                                    pref?.putString("userid",user!!.getUid().toString())
                                    pref?.apply()

                                    val modalbottomSheetFragment = CustomBottomSheetDialogFragment()
                                    activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }
                                }

                                promonel.setText(user!!.getFullname())

                                promonel.setOnClickListener {
                                    val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                                    pref?.putString("userid",user!!.getUid().toString())
                                    pref?.apply()

                                    val modalbottomSheetFragment = CustomBottomSheetDialogFragment()
                                    activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }
                                }

                                fail.setOnClickListener {
                                    val dialIntent = Intent(Intent.ACTION_DIAL)
                                    dialIntent.data = Uri.parse("tel:" + user.getMobile())
                                    startActivity(dialIntent)
                                }
                                faidl.setOnClickListener {
                                    val packageManager = context!!.packageManager
                                    val toNumber:String = "254"+user.getMobile()
                                    val mimi = job.getTitle()
                                    val indi = user.getFullname()
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
                    Glide.with(context!!)  //2
                        .load(job.getImage()) //3
                        .centerCrop() //4
                        .into(view!!.mawazol)
                    view?.kisumul?.text = job!!.getTitle()
                    view?.kiamunyil?.text = "Condition: "+job!!.getCondition()
                    view?.mombasal?.text = "Type: "+job!!.getType()
                    view?.nakurul?.text = "Ksh "+job!!.getPrice()
                    view?.liteinl?.text = job!!.getLocation()
                    view?.rashfordl?.text = job!!.getDescription()



                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })






        return view
    }


}