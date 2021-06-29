package com.example.swayy

import android.content.Intent
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
import com.example.swayy.Adapter.ItemsAdapter
import com.example.swayy.Adapter.StoreAdapter
import com.example.swayy.Adapter.saveditemAdapter
import com.example.swayy.model.*
import com.example.swayy.stores.storesActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_not.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.fragment_home2.shimmerFrameLayout
import kotlinx.android.synthetic.main.fragment_saledetail.*

class favoriteFragment : Fragment() {
    private var itemsAdapter: ItemsAdapter? = null
    private var saveditemadapter: saveditemAdapter? = null
    private var mSaved:MutableList<Saveditem>? = null
    private var mStore:MutableList<Store>? = null
    private lateinit var firebaseUser: FirebaseUser

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
        val view =  inflater.inflate(R.layout.fragment_favorite, container, false)

        if (FirebaseAuth.getInstance().currentUser != null)

        {
            view.minanyangoo.visibility = View.GONE
            firebaseUser = FirebaseAuth.getInstance().currentUser!!


//        view.btnSignupstores.setOnClickListener {
//            startActivity(Intent(context,storesActivity::class.java))
//        }

            var recyclerviewelectronics: RecyclerView? = null
            var num = 2
            recyclerviewelectronics = view.findViewById(R.id.recycler_view_electronics)
            recyclerviewelectronics?.setHasFixedSize(true)
            recyclerviewelectronics.itemAnimator = DefaultItemAnimator()
            val linearLayoutt = GridLayoutManager(context,num)
            linearLayoutt.reverseLayout = false

            linearLayoutt.orientation = LinearLayoutManager.VERTICAL
            recyclerviewelectronics?.layoutManager = linearLayoutt
            mSaved = ArrayList()
            saveditemadapter = context?.let { saveditemAdapter(it,mSaved as ArrayList<Saveditem>,true) }
            recyclerviewelectronics?.adapter = saveditemadapter
            recyclerviewelectronics?.visibility = View.VISIBLE



            AndroidNetworking.initialize(context)
            AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
                .build()
                .getAsObjectList(Saveditem::class.java, object : ParsedRequestListener<List<Saveditem>> {
                    override fun onResponse(saved: List<Saveditem>) {
                        shimmerFrameLayout.stopShimmerAnimation()
                        shimmerFrameLayout.visibility = View.GONE
//                    recycler_view_post.visibility = View.VISIBLE
                        recycler_view_electronics.visibility = View.VISIBLE


                        val userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)

                        userRef.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot) {

                                if (p0.exists())
                                {
                                    val user = p0.getValue<User>(User::class.java)


                                    val testing = user!!.getUid()

                                    val jobsReff = FirebaseDatabase.getInstance().getReference("New").child(testing)
                                    jobsReff.addChildEventListener(object : ChildEventListener {
                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }

                                        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                                            TODO("Not yet implemented")
                                        }

                                        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                                            mSaved?.clear()

                                            if (snapshot.exists()){
                                                val job = snapshot.getValue(Saveditem::class.java)
                                                if (job != null)
                                                {
                                                    mSaved?.add(job)
                                                }
                                                saveditemadapter?.notifyDataSetChanged()


                                            }



                                        }


                                        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {


                                            if (snapshot.exists()){
                                                val job = snapshot.getValue(Saveditem::class.java)
                                                if (job != null)
                                                {
                                                    mSaved?.add(job)
                                                }
                                                saveditemadapter?.notifyDataSetChanged()
                                            }


                                        }

                                        override fun onChildRemoved(snapshot: DataSnapshot) {
                                            recyclerviewelectronics.visibility = View.GONE
                                        }

                                    })

                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })


                        ///////////////////////////////////////


                        ///////////////////////////////////////





                    }

                    override fun onError(anError: ANError) {
                        shimmerFrameLayout.visibility = View.GONE
                        recycler_view_electronics.visibility = View.GONE
                        naneanimation.visibility = View.VISIBLE
                    }
                })



        }



        return view
    }
    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser == null)
        {

            shimmerFrameLayout.visibility = View.GONE
            recycler_view_electronics.visibility = View.GONE
            minanyangoo.visibility = View.VISIBLE

            bahatio.setOnClickListener {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }
            textView28o.setOnClickListener {
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