package com.example.swayy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import com.example.swayy.settings.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlin.system.exitProcess

class profileFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        view.june.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                startActivity(Intent(context,feedbackActivity::class.java))

            }

        }
        view.may.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                startActivity(Intent(context,noteActivity::class.java))

            }

        }
        view.april.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {

                startActivity(Intent(context,chatActivity::class.java))
            }

        }
        view.march.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                startActivity(Intent(context,manageActivity::class.java))

            }

        }

        view.ndakugunda.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                startActivity(Intent(context,ProfileActivity::class.java))
            }

        }
        view.ndakugundasong.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val modalbottomSheetFragment = CustomLoginFragment()
                activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }else {
                startActivity(Intent(context,setActivity::class.java))
            }

        }

        view.accnad.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(context,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }
        if (FirebaseAuth.getInstance().currentUser != null)
        {

        }
        if (FirebaseAuth.getInstance().currentUser == null)
        {

            view.accnad.visibility = View.GONE
        }







        return view
    }



}