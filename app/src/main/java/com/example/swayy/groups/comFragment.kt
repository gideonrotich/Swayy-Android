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
import kotlinx.android.synthetic.main.fragment_com.*
import kotlinx.android.synthetic.main.fragment_com.view.*
import kotlinx.android.synthetic.main.fragment_sisi.*
import kotlinx.android.synthetic.main.fragment_sisi.view.*


class comFragment : Fragment() {
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
        val  view =  inflater.inflate(R.layout.fragment_com, container, false)
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()

        }
        view.ronaldocom.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",ronaldocom.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.neymarcom.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",neymarcom.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.smithcom.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",smithcom.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariacom.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariacom.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariamecom.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariamecom.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariayoucom.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariayoucom.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariawaocom.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariawaocom.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimarianextcom.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimarianextcom.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariapsgcom.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariapsgcom.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariapsgcomwewe.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariapsgcomwewe.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }

        return view
    }


}