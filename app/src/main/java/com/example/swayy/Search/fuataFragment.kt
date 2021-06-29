package com.example.swayy.Search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.swayy.model.Store
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_fuata.*
import kotlinx.android.synthetic.main.fragment_fuata.view.*
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.fragment_home2.shimmerFrameLayout


class fuataFragment : Fragment() {
    private lateinit var firebaseUser: FirebaseUser
    private var mItems: MutableList<Items>? = null
    private var itemsAdapter: ItemsAdapter? = null
    private lateinit var profileId:String
    private lateinit var swala:String
    private lateinit var lao:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_fuata, container, false)

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)



        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()
            this.swala = pref.getString("swala","none").toString()
            this.lao = pref.getString("lao","none").toString()
        }


        var recyclerviewmoringa: RecyclerView? = null
        val num = 2

        recyclerviewmoringa = view.findViewById(R.id.recycler_view_moringa)
        recyclerviewmoringa?.setHasFixedSize(true)
        recyclerviewmoringa?.itemAnimator = DefaultItemAnimator()
        val linearLayout = GridLayoutManager(context, num)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.HORIZONTAL
        recyclerviewmoringa?.layoutManager = linearLayout
        mItems = ArrayList()
        itemsAdapter = context?.let { ItemsAdapter(it,mItems as ArrayList<Items>,true) }
        recyclerviewmoringa?.adapter = itemsAdapter
        recyclerviewmoringa?.visibility = View.VISIBLE

        view.lll.text = profileId

        AndroidNetworking.initialize(context)
        AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
            .build()
            .getAsObjectList(Store::class.java, object : ParsedRequestListener<List<Store>> {
                override fun onResponse(store: List<Store>) {
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
//                    recycler_view_post.visibility = View.VISIBLE
                    recycler_view_moringa.visibility = View.VISIBLE




        val query = FirebaseDatabase.getInstance().getReference(swala).child(lao)
            .orderByChild("jobtitle")
            .startAt(profileId).endAt(profileId + "\uf8ff")


        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot)
            {
                mItems?.clear()

                for (snapshot in p0.children)
                {
                    val job = snapshot.getValue(Items::class.java)
                    if (job != null)
                    {
                        mItems?.add(job)
                    }
                }

                itemsAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        val jobsRef = FirebaseDatabase.getInstance().getReference(swala).child(lao)
        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (view?.lll?.text.toString() == profileId)
                {
                    mItems?.clear()

                    for (snapshot in p0.children)
                    {
                        val job = snapshot.getValue(Items::class.java)
                        if (job != null)
                        {
                            mItems?.add(job)
                        }
                    }

                    itemsAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



    }

    override fun onError(anError: ANError) {
        shimmerFrameLayout.visibility = View.GONE
        recycler_view_electronics.visibility = View.GONE
        naneanimation.visibility = View.VISIBLE
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