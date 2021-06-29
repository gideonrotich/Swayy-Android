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
import kotlinx.android.synthetic.main.fragment_mz.*
import kotlinx.android.synthetic.main.fragment_mz.view.*

class mzFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_mz, container, false)

        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()

        }


        view.ronaldocommz.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",ronaldocommz.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.neymarcommz.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",neymarcommz.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.smithcommz.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",smithcommz.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariacommz.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariacommz.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariamecommz.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariamecommz.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariayoucommz.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariayoucommz.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariawaocommz.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariawaocommz.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimarianextcommz.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimarianextcommz.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariapsgcommz.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariapsgcommz.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariapsgcomwewemz.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariapsgcomwewemz.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariapsgcomwewemzone.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariapsgcomwewemzone.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariapsgcomwewemztwo.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariapsgcomwewemztwo.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariapsgcomwewemzthree.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariapsgcomwewemzthree.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        view.dimariapsgcomwewemzfour.setOnClickListener {
            val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("nextId",dimariapsgcomwewemzfour.text.toString())
            pref?.putString("profileId",profileId)
            pref?.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, finalFragment()).commit()
        }
        return view
    }


}