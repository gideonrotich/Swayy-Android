package com.example.swayy.stores

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.swayy.R
import com.example.swayy.message.ChatLogActivity
import com.example.swayy.message.NewMessageActivity
import com.example.swayy.message.UserItem
import com.example.swayy.model.Saveditem
import com.example.swayy.model.User
import com.example.swayy.model.Userr
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_followers.*
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.activity_new_message.recyclerview_newmessage
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class FollowersActivity : AppCompatActivity() {
    private lateinit var testing:String

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followers)

        val window: Window = this@FollowersActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@FollowersActivity,R.color.giddy)

        val pref = this?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.testing = pref.getString("testing","none").toString()

        }

//        val ref = FirebaseDatabase.getInstance().getReference("/Follow").child(testing).child("Followers")
//        ref.addListenerForSingleValueEvent(object: ValueEventListener {
//            override fun onDataChange(p0: DataSnapshot) {
//                val adapter = GroupAdapter<ViewHolder>()
//
//                p0.children.forEach {
//                     Log.d("NewMessage",it.toString())
//                    val userr = it.getValue(Userr::class.java)
//                    if (userr != null){
//                        adapter.add(UserItem(userr))
//                    }
//
//                }
//
//
//
//
//                adapter.setOnItemClickListener { item, view ->
//                    val userItem = item as UserItem
//
//                    val intent = Intent(view.context, ChatLogActivity::class.java)
//                    intent.putExtra(FollowersActivity.USER_KEY,userItem.userr)
////                    intent.putExtra(USER_KEY_IMAGE,userItem.user.getImage())
////                    intent.putExtra(USER_KEY_PROFESSION,userItem.user.getProfession())
////                    intent.putExtra(USER_KEY_UID,userItem.user.getUid())
//                    startActivity(intent)
//                    finish()
//                }
//
//                recyclerview_newmessagem.adapter = adapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })


        val userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(testing)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<User>(User::class.java)


                    val testingone = user!!.getUid()

                    val jobsReff = FirebaseDatabase.getInstance().getReference("Follow").child(testingone).child("Followers")
                    jobsReff.addChildEventListener(object : ChildEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                            TODO("Not yet implemented")
                        }

                        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                            val adapter = GroupAdapter<ViewHolder>()

                            if (snapshot.exists()){
                                Log.d("NewMessage",snapshot.toString())
                                val userr = snapshot.getValue(Userr::class.java)
                                if (userr != null){
                                    adapter.add(UserItem(userr))
                                }

                            }




                            adapter.setOnItemClickListener { item, view ->
                                val userItem = item as UserItem

                                val intent = Intent(view.context, ChatLogActivity::class.java)
                                intent.putExtra(FollowersActivity.USER_KEY,userItem.userr)
//                    intent.putExtra(USER_KEY_IMAGE,userItem.user.getImage())
//                    intent.putExtra(USER_KEY_PROFESSION,userItem.user.getProfession())
//                    intent.putExtra(USER_KEY_UID,userItem.user.getUid())
                                startActivity(intent)
                                finish()
                            }

                            recyclerview_newmessagem.adapter = adapter

                        }


                        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {


                            val adapter = GroupAdapter<ViewHolder>()

                            if (snapshot.exists()) {
                                Log.d("NewMessage",snapshot.toString())
                                val userr = snapshot.getValue(Userr::class.java)
                                if (userr != null){
                                    adapter.add(UserItem(userr))
                                }

                            }




                            adapter.setOnItemClickListener { item, view ->
                                val userItem = item as UserItem

                                val intent = Intent(view.context, ChatLogActivity::class.java)
                                intent.putExtra(FollowersActivity.USER_KEY,userItem.userr)
//                    intent.putExtra(USER_KEY_IMAGE,userItem.user.getImage())
//                    intent.putExtra(USER_KEY_PROFESSION,userItem.user.getProfession())
//                    intent.putExtra(USER_KEY_UID,userItem.user.getUid())
                                startActivity(intent)
                                finish()
                            }

                            recyclerview_newmessagem.adapter = adapter
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
    companion object {
        val USER_KEY = "USER_KEY"

    }


}
class UserItem(val userr: Userr): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username_textview.text = userr.fullname
//        Picasso.get().load(userr.image).placeholder(R.drawable.onee).into(viewHolder.itemView.image_list)

    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}