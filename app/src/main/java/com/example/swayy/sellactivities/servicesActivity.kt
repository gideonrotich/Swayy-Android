package com.example.swayy.sellactivities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.swayy.R
import com.example.swayy.categories.yoteActivity
import kotlinx.android.synthetic.main.activity_electronics.*
import kotlinx.android.synthetic.main.activity_seeking.*
import kotlinx.android.synthetic.main.activity_services.*

class servicesActivity : AppCompatActivity() {
    private lateinit var profileId:String
    private lateinit var storeid:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()
            this.storeid = pref.getString("storeid","none").toString()
        }


        val spinnertwo = findViewById<Spinner>(R.id.vitu)
        if (spinnertwo != null) {

            val electronics = resources.getStringArray(R.array.services)
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
                    wapionevitu.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
        btnnextvitu.setOnClickListener {
            val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("nextId",wapionevitu.text.toString())
            pref.putString("profileId",profileId)
            pref.putString("storeid",storeid)
            pref.apply()
            startActivity(Intent(this, yoteActivity::class.java))
        }
    }
}