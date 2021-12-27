package com.example.swayy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_input.*
import kotlinx.android.synthetic.main.activity_itemsale.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.etFirstNameone

class inputActivity : AppCompatActivity() {


    private lateinit var firebaseUser: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)



        firebaseUser = FirebaseAuth.getInstance().currentUser!!



        val languages = resources.getStringArray(R.array.Languages)
        val spinner = findViewById<Spinner>(R.id.techtextoneo)
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
                    wapioneo.text = me

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
        btnUpdateo.setOnClickListener{
            inputitems()

        }





    }





    private fun inputitems() {
        when{
            etinput.text.toString() == "" -> {
                Toast.makeText(this,"Write item first", Toast.LENGTH_LONG).show()
            }
            else->{
                val me = wapioneo.text.toString()
                val jobsRef = FirebaseDatabase.getInstance().reference.child("Maincategories").child(me)
                val time = System.currentTimeMillis().toString()

                val userMap = HashMap<String, Any>()
                userMap["maincategory"] = me
                userMap["categoryitem"] = etinput.text.toString()

                jobsRef.child(time).updateChildren(userMap)

                Toast.makeText(this,"Categories has been updated successfully", Toast.LENGTH_LONG).show()

            }
        }
    }
}