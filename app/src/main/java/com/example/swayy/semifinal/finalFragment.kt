package com.example.swayy.semifinal

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.swayy.Adapter.ItemsAdapter
import com.example.swayy.R
import com.example.swayy.model.Items
import com.example.swayy.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_final.*
import kotlinx.android.synthetic.main.fragment_final.view.*
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.fragment_home2.shimmerFrameLayout

class finalFragment : Fragment() {
    private var itemsAdapter: ItemsAdapter? = null
    private var mItems:MutableList<Items>? = null
    private var mUser:MutableList<User>? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var profileId:String
    private lateinit var nextId:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_final, container, false)


        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()
            this.nextId = pref.getString("nextId","none").toString()
        }

        view.sawa.text = nextId

        var recyclerviewsale: RecyclerView? = null

        var num = 2
        recyclerviewsale = view.findViewById(R.id.recycler_view_sale)
        recyclerviewsale?.setHasFixedSize(true)
        recyclerviewsale.itemAnimator = DefaultItemAnimator()
        val linearLayout = GridLayoutManager(context,num)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        recyclerviewsale?.layoutManager = linearLayout
        mItems = ArrayList()
        itemsAdapter = context?.let { ItemsAdapter(it,mItems as ArrayList<Items>,true) }
        recyclerviewsale?.adapter = itemsAdapter
        recyclerviewsale?.visibility = View.VISIBLE


        AndroidNetworking.initialize(context)
        AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
            .build()
            .getAsObjectList(Items::class.java, object : ParsedRequestListener<List<Items>> {
                override fun onResponse(items: List<Items>) {
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
//                    recycler_view_post.visibility = View.VISIBLE

                    val jobsRef = FirebaseDatabase.getInstance().getReference(profileId).child(nextId)
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


                    recycler_view_sale.visibility = View.VISIBLE




                }

                override fun onError(anError: ANError) {
                    shimmerFrameLayout.visibility = View.GONE
                    recycler_view_sale.visibility = View.GONE
                    Toast.makeText(context, "Check your Internet connection..", Toast.LENGTH_LONG).show()
                }
            })





        return view
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