package com.example.swayy.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.swayy.Adapter.CommentsAdapter
import com.example.swayy.Adapter.ItemsAdapter
import com.example.swayy.Adapter.NotificationAdapter
import com.example.swayy.CustomBottomSheetDialogFragment
import com.example.swayy.R
import com.example.swayy.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_not.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.fragment_home2.shimmerFrameLayout

class noteActivity : AppCompatActivity() {
    private var notificationAdapter: NotificationAdapter? = null
    private var mNotification:MutableList<Notification>? = null
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!




        var recyclerView: RecyclerView
        recyclerView = findViewById(R.id.recycler_view_notifications)
        val linearLayoutManager =  LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        recyclerView.layoutManager = linearLayoutManager

        mNotification = ArrayList()
        notificationAdapter = this?.let { NotificationAdapter(it, mNotification) }
        recyclerView.adapter = notificationAdapter


        AndroidNetworking.initialize(this)
        AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
            .build()
            .getAsObjectList(Saveditem::class.java, object :
                ParsedRequestListener<List<Notification>> {
                override fun onResponse(saved: List<Notification>) {
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
//                    recycler_view_post.visibility = View.VISIBLE
                    recycler_view_notifications.visibility = View.VISIBLE


                    val commentsRef = FirebaseDatabase.getInstance().reference.child("Notifications").child(firebaseUser.uid)

                    commentsRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {
                            if (p0.exists())
                            {
                                mNotification!!.clear()

                                for (snapshot in p0.children)
                                {
                                    val comment = snapshot.getValue(Notification::class.java)
                                    mNotification!!.add(comment!!)
                                }

                                notificationAdapter!!.notifyDataSetChanged()
                            }
                            else{
                                coco.visibility = View.VISIBLE
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })



                }

                override fun onError(anError: ANError) {
                    shimmerFrameLayout.visibility = View.GONE
                    recycler_view_notifications.visibility = View.GONE

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

}