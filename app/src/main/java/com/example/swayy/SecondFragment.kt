package com.example.swayy

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.swayy.Adapter.CategoryAdapter
import com.example.swayy.Adapter.ItemsAdapter
import com.example.swayy.Adapter.TrendAdapter
import com.example.swayy.model.Category
import com.example.swayy.model.Items
import com.example.swayy.model.Trend
import com.example.swayy.model.User
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.fragment_home2.view.*
import kotlinx.android.synthetic.main.fragment_kaba.view.*
import kotlinx.android.synthetic.main.fragment_second.view.*


class SecondFragment : Fragment() {
    private var categoryAdapter: CategoryAdapter? = null
    private var mCategory: MutableList<Category>? = null
    private var mUser: MutableList<User>? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var profileId:String
    private lateinit var mSwipe: SwipeRefreshLayout

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
        val view =  inflater.inflate(R.layout.fragment_second, container, false)

        view.swipe_maina.post(Runnable {
            view.swipe_maina.setRefreshing(true)
            //loading method
            setupAPICall()
        })

        view.swipe_maina.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            //loading method
            setupAPICall()
        })

        //



        //


        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()

        }

        var recyclerviewcat: RecyclerView? = null

        recyclerviewcat = view.findViewById(R.id.recycler_view_cat)
        recyclerviewcat?.setHasFixedSize(true)
        recyclerviewcat.itemAnimator = DefaultItemAnimator()
        val linearLayoutt = LinearLayoutManager(context)
        linearLayoutt.reverseLayout = true
        linearLayoutt.stackFromEnd = true
        linearLayoutt.orientation = LinearLayoutManager.VERTICAL
        recyclerviewcat?.layoutManager = linearLayoutt
        mCategory = ArrayList()
        categoryAdapter = context?.let { CategoryAdapter(it, mCategory as ArrayList<Category>, true) }
        recyclerviewcat?.adapter = categoryAdapter
        recyclerviewcat?.visibility = View.VISIBLE

        view?.rosal?.text = profileId





        return view
    }
    private fun setupAPICall() {
        AndroidNetworking.initialize(context)
        AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
            .build()
                .getAsObjectList(Category::class.java, object : ParsedRequestListener<List<Category>> {
                override fun onResponse(items: List<Category>) {

                    view?.swipe_maina?.setRefreshing(false)
//                    view.recycler_view_post.visibility = View.VISIBLE
                    checknet()


//





                }

                override fun onError(anError: ANError) {
                    Toast.makeText(context,"Check your internet",Toast.LENGTH_LONG).show()


                }
            })
    }

    private fun checknet() {
        if (profileId == "Agriculture and food"){

            val jobsReff = FirebaseDatabase.getInstance().getReference("Maincategories").child("Agriculture and food")
            jobsReff.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    mCategory?.clear()

                    for (snapshot in p0.children) {
                        val job = snapshot.getValue(Category::class.java)
                        if (job != null) {
                            mCategory?.add(job)

                        }
                        categoryAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }
        if (profileId == "Animals and pets"){

            val jobsReff = FirebaseDatabase.getInstance().getReference("Maincategories").child("Animals and pets")
            jobsReff.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    mCategory?.clear()

                    for (snapshot in p0.children) {
                        val job = snapshot.getValue(Category::class.java)
                        if (job != null) {
                            mCategory?.add(job)
                        }
                        categoryAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }
        if (profileId == "Home furniture and appliances"){

            val jobsReff = FirebaseDatabase.getInstance().getReference("Maincategories").child("Home furniture and appliances")
            jobsReff.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    mCategory?.clear()

                    for (snapshot in p0.children) {
                        val job = snapshot.getValue(Category::class.java)
                        if (job != null) {
                            mCategory?.add(job)
                        }
                        categoryAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }
        if (profileId == "Electronics"){

            val jobsReff = FirebaseDatabase.getInstance().getReference("Maincategories").child("Electronics")
            jobsReff.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    mCategory?.clear()

                    for (snapshot in p0.children) {
                        val job = snapshot.getValue(Category::class.java)
                        if (job != null) {
                            mCategory?.add(job)
                        }
                        categoryAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }
        if (profileId == "Vehicles"){

            val jobsReff = FirebaseDatabase.getInstance().getReference("Maincategories").child("Vehicles")
            jobsReff.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    mCategory?.clear()

                    for (snapshot in p0.children) {
                        val job = snapshot.getValue(Category::class.java)
                        if (job != null) {
                            mCategory?.add(job)
                        }
                        categoryAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }
        if (profileId == "Health and beauty"){

            val jobsReff = FirebaseDatabase.getInstance().getReference("Maincategories").child("Health and beauty")
            jobsReff.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    mCategory?.clear()

                    for (snapshot in p0.children) {
                        val job = snapshot.getValue(Category::class.java)
                        if (job != null) {
                            mCategory?.add(job)
                        }
                        categoryAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }
        if (profileId == "Fashion"){

            val jobsReff = FirebaseDatabase.getInstance().getReference("Maincategories").child("Fashion")
            jobsReff.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    mCategory?.clear()

                    for (snapshot in p0.children) {
                        val job = snapshot.getValue(Category::class.java)
                        if (job != null) {
                            mCategory?.add(job)
                        }
                        categoryAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }
        if (profileId == "Mobile phones and tablets"){

            val jobsReff = FirebaseDatabase.getInstance().getReference("Maincategories").child("Mobile phones and tablets")
            jobsReff.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    mCategory?.clear()

                    for (snapshot in p0.children) {
                        val job = snapshot.getValue(Category::class.java)
                        if (job != null) {
                            mCategory?.add(job)
                        }
                        categoryAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }
        if (profileId == "Property"){

            val jobsReff = FirebaseDatabase.getInstance().getReference("Maincategories").child("Property")
            jobsReff.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    mCategory?.clear()

                    for (snapshot in p0.children) {
                        val job = snapshot.getValue(Category::class.java)
                        if (job != null) {
                            mCategory?.add(job)
                        }
                        categoryAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBanners()
    }

    fun loadBanners() {
        val adRequest = AdRequest.Builder().build()
        view?.adViewa?.loadAd(adRequest)
    }



}