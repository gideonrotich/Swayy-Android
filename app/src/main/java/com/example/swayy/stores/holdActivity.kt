package com.example.swayy.stores

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.FragmentActivity
import com.example.swayy.R
import com.example.swayy.groups.sisiFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_hold.*
import kotlinx.android.synthetic.main.fragment_home2.view.*

class holdActivity : AppCompatActivity() {
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hold)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val languages = resources.getStringArray(R.array.stores)

        val spinner = findViewById<Spinner>(R.id.mbele)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, languages
            )
            spinner.adapter = adapter


            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = languages[position]
                    wapionembele.text = me

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

       btnnextkuja.setOnClickListener {
            val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
            pref?.putString("kujaid",wapionembele.text.toString())
            pref?.apply()
            startActivity(Intent(this,storesActivity::class.java))
        }

    }
}