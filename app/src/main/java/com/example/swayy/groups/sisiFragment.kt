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
import kotlinx.android.synthetic.main.fragment_sisi.view.*


class sisiFragment : Fragment() {
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
        val view =  inflater.inflate(R.layout.fragment_sisi, container, false)

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()

        }
        view.ronaldo.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",ronaldo.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,finalFragment()).commit()
        }
        view.neymar.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",neymar.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,finalFragment()).commit()
        }
        view.smith.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",smith.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,finalFragment()).commit()
        }
        view.dimaria.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimaria.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,finalFragment()).commit()
        }
        view.dimariame.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariame.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,finalFragment()).commit()
        }
        view.dimariayou.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariayou.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,finalFragment()).commit()
        }
        view.dimariawao.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariawao.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,finalFragment()).commit()
        }
        view.dimarianext.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimarianext.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,finalFragment()).commit()
        }
        view.dimariapsg.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariapsg.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,finalFragment()).commit()
        }

        return view
    }



}