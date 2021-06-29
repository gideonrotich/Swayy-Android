package com.example.swayy

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_sell.*
import kotlinx.android.synthetic.main.fragment_sell.view.*

class sellFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_sell, container, false)
        if (FirebaseAuth.getInstance().currentUser != null)
        {
            view.btnnext.setOnClickListener {
                startActivity(Intent(context,ItemsaleActivity::class.java))
            }

        }
        if (FirebaseAuth.getInstance().currentUser == null)
        {
            view.btnnext.setOnClickListener {
                startActivity(Intent(context,SignupActivity::class.java))
            }

        }


        return view
    }


}