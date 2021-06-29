package com.example.swayy.groups

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import com.example.swayy.R
import com.example.swayy.homeFragment
import com.example.swayy.semifinal.finalFragment
import kotlinx.android.synthetic.main.fragment_nyumba.*
import kotlinx.android.synthetic.main.fragment_nyumba.view.*
import kotlinx.android.synthetic.main.fragment_sisi.*
import kotlinx.android.synthetic.main.fragment_sisi.view.*


class nyumbaFragment : Fragment() {
    private lateinit var profileId:String

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
        val view =  inflater.inflate(R.layout.fragment_nyumba, container, false)

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()

        }
        view.ronaldoe.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",ronaldoe.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.neymare.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",neymare.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.smithe.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",smithe.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariae.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariae.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariamee.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariamee.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariayoue.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariayoue.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariawaoe.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariawaoe.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimarianexte.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimarianexte.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }



        return view
    }


}