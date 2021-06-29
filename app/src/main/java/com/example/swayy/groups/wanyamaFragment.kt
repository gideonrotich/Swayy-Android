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
import kotlinx.android.synthetic.main.fragment_sisi.*
import kotlinx.android.synthetic.main.fragment_sisi.dimariawao
import kotlinx.android.synthetic.main.fragment_sisi.view.*
import kotlinx.android.synthetic.main.fragment_sisi.view.dimariawao
import kotlinx.android.synthetic.main.fragment_wanyama.*
import kotlinx.android.synthetic.main.fragment_wanyama.view.*

class wanyamaFragment : Fragment() {
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
        val view =  inflater.inflate(R.layout.fragment_wanyama, container, false)

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()

        }
        view.ronaldog.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",ronaldog.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.neymarg.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",neymarg.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.smithg.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",smithg.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariag.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariag.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariameg.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariameg.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariayoug.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariayoug.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariawaog.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariawaog.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }

        return view
    }


}