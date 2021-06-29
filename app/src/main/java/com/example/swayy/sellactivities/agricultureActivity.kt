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
import com.example.swayy.categories.*
import kotlinx.android.synthetic.main.activity_agriculture.*
import kotlinx.android.synthetic.main.activity_itemsale.*

class agricultureActivity : AppCompatActivity() {
    private lateinit var profileId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agriculture)
        val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()

        }


        val spinnertwo = findViewById<Spinner>(R.id.shamba)
            if (spinnertwo != null) {

                    val electronics = resources.getStringArray(R.array.agriculture)
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
                            wapioneshamba.text = me
                            if(wapioneshamba.text == "Farm machinery and equipment")
                            {
                                btnnextagri.visibility = View.VISIBLE
                            }
                            if(wapioneshamba.text != "Farm machinery and equipment")
                            {
                                btnnextagri.visibility = View.GONE
                            }
                            if(wapioneshamba.text == "Feeds supplements and seeds")
                            {
                                btnnextfeeds.visibility = View.VISIBLE
                            }
                            if(wapioneshamba.text != "Feeds supplements and seeds")
                            {
                                btnnextfeeds.visibility = View.GONE
                            }
                            if(wapioneshamba.text == "Livestock and poultry")
                            {
                                btnnextlivestock.visibility = View.VISIBLE
                            }
                            if(wapioneshamba.text != "Livestock and poultry")
                            {
                                btnnextlivestock.visibility = View.GONE
                            }
                            if(wapioneshamba.text == "Meals and drinks")
                            {
                                btnnextmeals.visibility = View.VISIBLE
                            }
                            if(wapioneshamba.text != "Meals and drinks")
                            {
                                btnnextmeals.visibility = View.GONE
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }

                }

        btnnextagri.setOnClickListener {
            val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("nextId",wapioneshamba.text.toString())
            pref.putString("profileId",profileId)
            pref.apply()
            startActivity(Intent(this,yoteActivity::class.java))
        }
        btnnextfeeds.setOnClickListener {
            val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("nextId",wapioneshamba.text.toString())
            pref.putString("profileId",profileId)
            pref.apply()
            startActivity(Intent(this,yoteActivity::class.java))
        }
        btnnextlivestock.setOnClickListener {
            val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("nextId",wapioneshamba.text.toString())
            pref.putString("profileId",profileId)
            pref.apply()
            startActivity(Intent(this,yoteActivity::class.java))
        }
        btnnextmeals.setOnClickListener {
            val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("nextId",wapioneshamba.text.toString())
            pref.putString("profileId",profileId)
            pref.apply()
            startActivity(Intent(this,yoteActivity::class.java))
        }

    }
}