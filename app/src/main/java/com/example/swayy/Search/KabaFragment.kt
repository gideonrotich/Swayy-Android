package com.example.swayy.Search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
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
import com.example.swayy.R
import com.example.swayy.homeFragment
import com.example.swayy.model.Items
import com.example.swayy.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_animals.*
import kotlinx.android.synthetic.main.activity_electronics.*
import kotlinx.android.synthetic.main.activity_electronics.wapionevifaa
import kotlinx.android.synthetic.main.activity_equipment.*
import kotlinx.android.synthetic.main.activity_farm.*
import kotlinx.android.synthetic.main.activity_fashion.*
import kotlinx.android.synthetic.main.activity_furniture.*
import kotlinx.android.synthetic.main.activity_health.*
import kotlinx.android.synthetic.main.activity_jobs.*
import kotlinx.android.synthetic.main.activity_mobile.*
import kotlinx.android.synthetic.main.activity_property.*
import kotlinx.android.synthetic.main.activity_repair.*
import kotlinx.android.synthetic.main.activity_seeking.*
import kotlinx.android.synthetic.main.activity_services.*
import kotlinx.android.synthetic.main.activity_vehicles.*
import kotlinx.android.synthetic.main.fragment_kaba.*
import kotlinx.android.synthetic.main.fragment_kaba.view.*


class KabaFragment : Fragment() {
    private var itemsAdapter: ItemsAdapter? = null
    private var mItems:MutableList<Items>? = null
    private var mUser:MutableList<User>? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var shad:String
    private lateinit var kaba:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, tafutaFragment()).commit()
            }
        })
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_kaba, container, false)

        view.optome.setOnClickListener {
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, tafutaFragment()).commit()
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val pref = requireContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null) {
            this.shad = pref.getString("shad", "none").toString()
            this.kaba = pref.getString("kaba", "none").toString()

        }





        var recyclerviewmodcom: RecyclerView? = null

        val num = 2

        recyclerviewmodcom = view.findViewById(R.id.recycler_view_search)
        recyclerviewmodcom?.setHasFixedSize(true)
        recyclerviewmodcom?.itemAnimator = DefaultItemAnimator()
        val linearLayout = GridLayoutManager(context,num)
        linearLayout.reverseLayout = true
        linearLayout.orientation = LinearLayoutManager.VERTICAL
        recyclerviewmodcom?.layoutManager = linearLayout
        mItems = ArrayList()
        itemsAdapter = context?.let { ItemsAdapter(it,mItems as ArrayList<Items>,true) }
        recyclerviewmodcom?.adapter = itemsAdapter
        recyclerviewmodcom?.visibility = View.VISIBLE







        view.hua.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }



            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (view.hua.text.toString() == "")
                {

                }
                else
                {
                    btnSignInfacebookd.setOnClickListener {

                        val query = FirebaseDatabase.getInstance().getReference(shad).child(kaba)
                            .orderByChild("title")
                            .startAt(s.toString().toLowerCase()).endAt(s.toString().toLowerCase() + "\uf8ff")


                        query.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot)
                            {
                                mItems?.clear()
                                if (p0.exists())
                                {
                                    for (snapshot in p0.children)
                                    {
                                        val job = snapshot.getValue(Items::class.java)
                                        if (job != null)
                                        {
                                            mItems?.add(job)
//                                            view!!.mmm.visibility = View.GONE
                                        }

                                    }
                                    itemsAdapter?.notifyDataSetChanged()
                                }
                                else
                                {
                                    Toast.makeText(context,"Item not found",Toast.LENGTH_SHORT).show()
                                }



                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })

                        val jobsRefm = FirebaseDatabase.getInstance().getReference(shad).child(kaba)
                        jobsRefm.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot) {
                                if (view?.hua?.text.toString() == "")
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
                        ///////////////////////////////////////////////////////////

                        val searchtext = hua.text.toString()

                        when {
                            TextUtils.isEmpty(searchtext) -> Toast.makeText(
                                context,
                                "input search item first",
                                Toast.LENGTH_LONG
                            ).show()

                            else ->{
                                val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
                                val time = System.currentTimeMillis().toString()
                                val verifiedRef: DatabaseReference =
                                    FirebaseDatabase.getInstance().reference.child("Searchtext").child(firebaseUser.uid)
                                val key = verifiedRef.push().key

                                val userMap = HashMap<String, Any>()
                                userMap["searchtext"] = searchtext.toLowerCase()
                                userMap["uid"] = currentUserID
                                userMap["category"] = shad
                                userMap["subcategory"] = kaba


                                if (key != null) {
                                    verifiedRef.child(key).setValue(userMap)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {


                                            } else {
                                                val message = task.exception!!.toString()
                                                Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
                                                FirebaseAuth.getInstance().signOut()

                                            }
                                        }
                                }
                            }
                        }






                        /////////////////////////////////////////////////////////






                    }



                }
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })





        return view
    }


}