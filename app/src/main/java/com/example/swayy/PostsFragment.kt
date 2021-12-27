package com.example.swayy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swayy.Adapter.ItemsAdapter
import com.example.swayy.Adapter.StoreAdapter
import com.example.swayy.Adapter.TafutaAdapter
import com.example.swayy.model.Saveditem
import com.example.swayy.model.Store
import com.example.swayy.model.Tafuta
import com.example.swayy.model.Trend
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PostsFragment : Fragment() {
    private var tafutaAdapter: TafutaAdapter? = null
    private var mTafuta:MutableList<Tafuta>? = null
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_posts, container, false)


        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        var recyclerviewpos: RecyclerView? = null

        recyclerviewpos = view.findViewById(R.id.recycler_view_myposts)
        recyclerviewpos?.setHasFixedSize(true)
        recyclerviewpos.itemAnimator = DefaultItemAnimator()
        val linearLayoutta = LinearLayoutManager(context)
        linearLayoutta.reverseLayout = true
        linearLayoutta.stackFromEnd = true
        linearLayoutta.orientation = LinearLayoutManager.VERTICAL
        recyclerviewpos?.layoutManager = linearLayoutta
        mTafuta = ArrayList()
        tafutaAdapter = context?.let { TafutaAdapter(it, mTafuta as ArrayList<Tafuta>, true) }
        recyclerviewpos?.adapter = tafutaAdapter
        recyclerviewpos?.visibility = View.VISIBLE

        retrieveTrend()


        return view
    }

    private fun retrieveTrend() {
        val jobsReff = FirebaseDatabase.getInstance().getReference("Search")
            .child("Items").child(firebaseUser.uid)
        jobsReff.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                mTafuta?.clear()

                for (snapshot in p0.children) {
                    val job = snapshot.getValue(Tafuta::class.java)
                    if (job != null) {
                        mTafuta?.add(job)
                    }
                    tafutaAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }



}