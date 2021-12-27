package com.example.swayy.categories

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swayy.*
import com.example.swayy.Adapter.CommentsAdapter
import com.example.swayy.Adapter.ItemsAdapter
import com.example.swayy.Adapter.TafutaAdapter
import com.example.swayy.message.ChatLogActivity
import com.example.swayy.message.UserItem
import com.example.swayy.model.*
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_kaba.view.*
import kotlinx.android.synthetic.main.fragment_saledetail.*
import kotlinx.android.synthetic.main.fragment_saledetail.view.*
import kotlinx.android.synthetic.main.fragment_saledetail.view.pichalike
import kotlinx.android.synthetic.main.fragment_searchdetail.*
import kotlinx.android.synthetic.main.fragment_searchdetail.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class searchdetailFragment : Fragment() {
    private lateinit var profileId:String
    private lateinit var firebaseUser: FirebaseUser
    private var tafutaAdapter: TafutaAdapter? = null
    private var mTafuta:MutableList<Tafuta>? = null
    private var commentAdapter: CommentsAdapter? = null
    private var commentList: MutableList<Comment>? = null

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
        val view =  inflater.inflate(R.layout.fragment_searchdetail, container, false)

        //testing

        if (FirebaseAuth.getInstance().currentUser != null)

        {
            view.minanyangoaa.visibility = View.GONE
            firebaseUser = FirebaseAuth.getInstance().currentUser!!


            var recyclerView: RecyclerView
            recyclerView = view.findViewById(R.id.recycler_view_commentsaa)
            val linearLayoutManager =  LinearLayoutManager(context)
            linearLayoutManager.reverseLayout = true
            recyclerView.layoutManager = linearLayoutManager

            commentList = ArrayList()
            commentAdapter = context?.let { CommentsAdapter(it, commentList) }
            recyclerView.adapter = commentAdapter

            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

            if (pref != null)
            {
                this.profileId = pref.getString("profileId","none").toString()

            }

            val jobssRef = FirebaseDatabase.getInstance().getReference("Search").child("Items")
            jobssRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    mTafuta?.clear()

                    for (snapshot in p0.children)
                    {
                        val job = snapshot.getValue(Tafuta::class.java)
                        if (job != null)
                        {
                            mTafuta?.add(job)
                        }
                        tafutaAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

            val jobsoneRef = FirebaseDatabase.getInstance().getReference().child("Search").child("Items").child(profileId)

            jobsoneRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
//                if (context != null)
//                {view?.harmo?.text = job!!.getJobtitle()

//                    return
//                }
                    if (p0.exists())
                    {
                        val job = p0.getValue<Tafuta>(Tafuta::class.java)
                        view.rosaaa.text = job!!.getTitle()

                        view.pichalikeaa.setOnClickListener {
                            if (view.pichalikeaa.tag == "Like")
                            {
                                FirebaseDatabase.getInstance().reference
                                    .child("Likes")
                                    .child(job!!.getPostid())
                                    .child(firebaseUser!!.uid)
                                    .setValue(true).addOnCompleteListener { task ->
                                        if (task.isSuccessful)
                                        {
                                            val sing = job!!.getUid()
                                            val notRef = FirebaseDatabase.getInstance().reference.child("Notifications").child(sing)

                                            val commentsMap = HashMap<String, Any>()
                                            commentsMap["notification"] = "liked your product"
                                            commentsMap["publisher"] = firebaseUser!!.uid
                                            commentsMap["image"] = job!!.getImage()

                                            notRef.push().setValue(commentsMap)
                                        }
                                    }

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
                                    view.pichalikeaa.setImageResource(R.drawable.ic_thumb_up)
                                    view.pichalikeaa.tag = "Liked"
                                }
                                else
                                {
                                    view.pichalikeaa.setImageResource(R.drawable.ic_likeone)
                                    view.pichalikeaa.tag = "Like"
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
                                    view.likesaa.text = p0.childrenCount.toString() + ""
                                }

                            }

                            override fun onCancelled(error: DatabaseError) {

                            }
                        })

                        val wewe = job!!.getUid()
                        val griez = job.getUid()

                        val jobsRefd = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser!!.uid)

                        jobsRefd.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot) {

                                if (p0.exists())
                                {
                                    val user = p0.getValue<User>(User::class.java)

                                    val mwao = user!!.getFullname()



                                    val jobsRef = FirebaseDatabase.getInstance().getReference().child("Users").child(wewe)

                                    jobsRef.addValueEventListener(object : ValueEventListener {
                                        override fun onDataChange(p0: DataSnapshot) {

                                            if (p0.exists())
                                            {
                                                val user = p0.getValue<User>(User::class.java)

                                                Picasso.get().load(user!!.getImage()).into(promaa)

                                                promoneaa.setText(user!!.getFullname())

                                                promaa.setOnClickListener {
                                                    val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                                                    pref?.putString("userid",user!!.getUid().toString())
                                                    pref?.apply()

                                                    val modalbottomSheetFragment = CustomBottomSheetDialogFragment()
                                                    activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }
                                                }

                                                promoneaa.setOnClickListener {
                                                    val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
                                                    pref?.putString("userid",user!!.getUid().toString())
                                                    pref?.apply()

                                                    val modalbottomSheetFragment = CustomBottomSheetDialogFragment()
                                                    activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }
                                                }

                                                faiaa.setOnClickListener {
                                                    val dialIntent = Intent(Intent.ACTION_DIAL)
                                                    dialIntent.data = Uri.parse("tel:" + user.getMobile())
                                                    startActivity(dialIntent)
                                                }
                                                faidaa.setOnClickListener {
                                                    val packageManager = context!!.packageManager
                                                    val toNumber:String = "254"+user.getMobile()
                                                    val mimi = job.getTitle()
                                                    val indi = user.getFullname()
                                                    val text:String = "Hello, am "+mwao +" and am interested in your " +mimi +" that you posted in the Swayy app"
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

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })



                        Glide.with(context!!)  //2
                            .load(job.getImage()) //3
                            .centerCrop() //4
                            .into(view!!.mawazoaa)
                        view?.kisumuaa?.text = job!!.getTitle()
                        view?.kiamunyiaa?.text = "Condition: "+job!!.getCondition()
                        view?.mombasaaa?.text = "Type: "+job!!.getType()
                        view?.nakuruaa?.text = "Ksh "+job!!.getPrice()
                        view?.liteinaa?.text = job!!.getLocation()
                        view?.rashfordaa?.text = job!!.getDescription()

                        view?.btnSignInzamaa.setOnClickListener {
                            if (etEmailzamaa!!.text.toString() == "")
                            {
                                Toast.makeText(context,"Please write comment first", Toast.LENGTH_LONG).show()
                            }
                            else
                            {
                                val dakar = job!!.getPostid()
                                val commentsRef = FirebaseDatabase.getInstance().reference.child("Comments").child(dakar)

                                val commentsMap = HashMap<String, Any>()
                                commentsMap["comment"] = view?.etEmailzamaa!!.text.toString()
                                commentsMap["publisher"] = firebaseUser!!.uid

                                commentsRef.push().setValue(commentsMap)

                                etEmailzamaa!!.text?.clear()
                            }
                        }


                        var nambari  = 3

                        val mresh = job!!.getPostid()

                        val commentsRef = FirebaseDatabase.getInstance().reference.child("Comments").child(mresh).limitToLast(nambari)

                        commentsRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot) {
                                if (p0.exists())
                                {
                                    commentList!!.clear()

                                    for (snapshot in p0.children)
                                    {
                                        val comment = snapshot.getValue(Comment::class.java)
                                        commentList!!.add(comment!!)
                                    }

                                    commentAdapter!!.notifyDataSetChanged()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {

                            }
                        })

                        view.tumaaa.setOnClickListener {

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
                                putExtra(
                                    Intent.EXTRA_TEXT, "Check out this "+job.getTitle()+" in the swayy app\n" +
                                        " https://play.google.com/store/apps/details?id=com.swayy.firstapp")
//                            putExtra(Intent.EXTRA_TITLE, "Check out  " +job.getTitle() + "shop in the swayy app")
                                type = "text/plain"



                            }

                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(shareIntent)


                        }



//                    val ref = FirebaseDatabase.getInstance().getReference("/Users")
//                    ref.addListenerForSingleValueEvent(object: ValueEventListener {
//                        override fun onDataChange(p0: DataSnapshot) {
//                            val adapter = GroupAdapter<ViewHolder>()
//
//                            p0.children.forEach {
//                                Log.d("NewMessage",it.toString())
//                                val userr = it.getValue(Userr::class.java)
//                                if (userr != null){
//                                    adapter.add(UserItem(userr))
//                                }
//
//                            }
//
//                            adapter.setOnItemClickListener { item, view ->
//                                val userItem = item as UserItem
//
//                                val intent = Intent(view.context,ChatLogActivity::class.java)
//                                intent.putExtra(saledetailFragment.USER_KEY,userItem.userr)
//
////                    intent.putExtra(USER_KEY_IMAGE,userItem.user.getImage())
////                    intent.putExtra(USER_KEY_PROFESSION,userItem.user.getProfession())
////                    intent.putExtra(USER_KEY_UID,userItem.user.getUid())
//                                startActivity(intent)
//
//                            }
//
//                            recyclerview_newmessage.adapter = adapter
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//
//                        }
//                    })



                        val diallo = FirebaseDatabase.getInstance().reference.child("Users").child(wewe)
                        diallo.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot) {
                                if (p0.exists())
                                {
                                    val ref = FirebaseDatabase.getInstance().getReference("/Users").child(wewe)
                                    ref.addListenerForSingleValueEvent(object: ValueEventListener {
                                        override fun onDataChange(p0: DataSnapshot) {
                                            val adapter = GroupAdapter<ViewHolder>()



                                            val userr = p0.getValue(Userr::class.java)
                                            if (userr != null){
                                                adapter.add(UserItem(userr))
                                            }



                                            adapter.setOnItemClickListener { item, view ->
                                                val userItem = item as UserItem

                                                val intent = Intent(view.context, ChatLogActivity::class.java)
                                                intent.putExtra(saledetailFragment.USER_KEY,userItem.userr)

//                    intent.putExtra(USER_KEY_IMAGE,userItem.user.getImage())
//                    intent.putExtra(USER_KEY_PROFESSION,userItem.user.getProfession())
//                    intent.putExtra(USER_KEY_UID,userItem.user.getUid())
                                                startActivity(intent)

                                            }

                                            recyclerview_newmessageaa.adapter = adapter
                                        }

                                        override fun onCancelled(error: DatabaseError) {

                                        }
                                    })
                                }

                            }

                            override fun onCancelled(error: DatabaseError) {

                            }
                        })




                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }

        view.accnadeaa.setOnClickListener {
            startActivity(Intent(context,ItemsaleActivity::class.java))
        }


        var recyclerviewml: RecyclerView? = null


        recyclerviewml = view.findViewById(R.id.recycler_view_mlaa)
        recyclerviewml?.setHasFixedSize(true)
        recyclerviewml.itemAnimator = DefaultItemAnimator()
        val linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        linearLayout.stackFromEnd = true
        linearLayout.orientation = LinearLayoutManager.HORIZONTAL
        recyclerviewml?.layoutManager = linearLayout
        mTafuta = ArrayList()
        tafutaAdapter = context?.let { TafutaAdapter(it,mTafuta as ArrayList<Tafuta>,true) }
        recyclerviewml?.adapter = tafutaAdapter
        recyclerviewml?.visibility = View.VISIBLE


        //

        return view
    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser == null)
        {
            tumaaa.visibility = View.GONE

            minaaa.visibility = View.GONE
            minanyangoaa.visibility = View.VISIBLE

            bahatiaa.setOnClickListener {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }
            textView28aa.setOnClickListener {
                val modalbottomSheetFragment = CustomSignupFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }
            }

        }

    }

    companion object {
        val USER_KEY = "USER_KEY"

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBanners()
    }

    fun loadBanners() {
        val adRequest = AdRequest.Builder().build()
        view?.adViewj?.loadAd(adRequest)
    }


}
