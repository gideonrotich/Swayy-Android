package com.example.swayy.Search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swayy.Adapter.ItemsAdapter
import com.example.swayy.Adapter.SearchAdapter
import com.example.swayy.Adapter.saveditemAdapter
import com.example.swayy.R
import com.example.swayy.homeFragment
import com.example.swayy.model.Items
import com.example.swayy.model.Saved
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home2.view.*
import kotlinx.android.synthetic.main.fragment_kaba.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_tafuta.*
import kotlinx.android.synthetic.main.fragment_tafuta.view.*

class SearchFragment : Fragment() {
    private var searchAdapter:SearchAdapter? = null
    private var mSaved:MutableList<Saved>? = null
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
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        view.optos.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,tafutaFragment()).commit()
        }


        var recyclerviewmoney: RecyclerView? = null

        val num = 2
        var recycler_view_ser: RecyclerView? = null

        recycler_view_ser = view.findViewById(R.id.recycler_view_money)
        recycler_view_ser?.setHasFixedSize(true)
        recycler_view_ser?.itemAnimator = DefaultItemAnimator()
        val linearLayoutsearc = LinearLayoutManager(context)
        linearLayoutsearc.reverseLayout = true
        linearLayoutsearc.orientation = LinearLayoutManager.VERTICAL
        linearLayoutsearc.stackFromEnd = true
        recycler_view_ser?.layoutManager = linearLayoutsearc
        mSaved = ArrayList()
        searchAdapter = context?.let { SearchAdapter(it,mSaved as ArrayList<Saved>,true) }
        recycler_view_ser?.adapter = searchAdapter
        recycler_view_ser?.visibility = View.VISIBLE


        val jobsReff = FirebaseDatabase.getInstance().getReference("Searchtext").child(firebaseUser.uid)
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
                    val job = snapshot.getValue(Saved::class.java)
                    if (job != null)
                    {
                        mSaved?.add(job)
                        view.money.visibility = View.VISIBLE
                    }
                    searchAdapter?.notifyDataSetChanged()
                }
                else
                {
                    Toast.makeText(context,"it is empty",Toast.LENGTH_SHORT).show()
                }

            }


            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                mSaved?.clear()

                if (snapshot.exists()){
                    val job = snapshot.getValue(Saved::class.java)
                    if (job != null)
                    {
                        mSaved?.add(job)
                        view.money.visibility = View.VISIBLE
                    }
                    searchAdapter?.notifyDataSetChanged()
                }
                else
                {
                    Toast.makeText(context,"it is empty",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

        })


        val languages = resources.getStringArray(R.array.Languages)
        val spinner = view.findViewById<Spinner>(R.id.kcome)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item, languages
            )
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val wao = languages[position]
                    wapionemama.text = wao

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }

            }

        }
        view.optomewk.setOnClickListener {
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, homeFragment()).commit()
        }

        view.btnnextfashion_mecan.setOnClickListener {
            val pref = requireContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("shad", view.wapionemama.text.toString())


            pref.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, tafutaFragment()).commit()


        }

        return view
    }


}