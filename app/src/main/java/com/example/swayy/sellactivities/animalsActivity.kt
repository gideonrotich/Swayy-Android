package com.example.swayy.sellactivities

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.swayy.R
import com.example.swayy.categories.dogsActivity
import com.example.swayy.categories.mealsActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_agriculture.*
import kotlinx.android.synthetic.main.activity_animals.*

class animalsActivity : AppCompatActivity() {

    private lateinit var profileId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals)


        val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()

        }


        val spinnertwo = findViewById<Spinner>(R.id.wanyama)
        if (spinnertwo != null) {

            val electronics = resources.getStringArray(R.array.animals)
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, electronics
            )
            spinnertwo.adapter = adapter
            spinnertwo.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionewanyama.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
        btnnextpets.setOnClickListener {
            val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("nextId",wapionewanyama.text.toString())
            pref.putString("profileId",profileId)
            pref.apply()
            startActivity(Intent(this, dogsActivity::class.java))
        }
    }

}