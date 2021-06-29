package com.example.swayy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.swayy.sellactivities.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_itemsale.*
import kotlinx.android.synthetic.main.fragment_saledetail.*
import kotlinx.android.synthetic.main.fragment_saledetail.view.*

class ItemsaleActivity : AppCompatActivity() {
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var storeid:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itemsale)

        if (FirebaseAuth.getInstance().currentUser != null)

        {
            minanyangor.visibility = View.GONE
            firebaseUser = FirebaseAuth.getInstance().currentUser!!

            val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

            if (pref != null)
            {
                this.storeid = pref.getString("storeid","none").toString()

            }



            val languages = resources.getStringArray(R.array.Languages)
            val spinner = findViewById<Spinner>(R.id.techtextone)
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
                        wapione.text = me
                        if(wapione.text == "Agriculture and food")
                        {
                            btnnext.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Agriculture and food")
                        {
                            btnnext.visibility = View.GONE
                        }
                        if(wapione.text == "Animals and pets")
                        {
                            btnnextanimals.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Animals and pets")
                        {
                            btnnextanimals.visibility = View.GONE
                        }
                        if(wapione.text == "Babies and kids")
                        {
                            btnnextbabies.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Babies and kids")
                        {
                            btnnextbabies.visibility = View.GONE
                        }
                        if(wapione.text == "Commercial equipment and tools")
                        {
                            btnnextequipment.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Commercial equipment and tools")
                        {
                            btnnextequipment.visibility = View.GONE
                        }
                        if(wapione.text == "Electronics")
                        {
                            btnnextelectronics.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Electronics")
                        {
                            btnnextelectronics.visibility = View.GONE
                        }
                        if(wapione.text == "Fashion and beauty")
                        {
                            btnnextfashion.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Fashion and beauty")
                        {
                            btnnextfashion.visibility = View.GONE
                        }
                        if(wapione.text == "Health and beauty")
                        {
                            btnnexthealth.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Health and beauty")
                        {
                            btnnexthealth.visibility = View.GONE
                        }
                        if(wapione.text == "Home furniture and appliances")
                        {
                            btnnextfurniture.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Home furniture and appliances")
                        {
                            btnnextfurniture.visibility = View.GONE
                        }
                        if(wapione.text == "Jobs")
                        {
                            btnnextjobs.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Jobs")
                        {
                            btnnextjobs.visibility = View.GONE
                        }
                        if(wapione.text == "Mobile phones and tablets")
                        {
                            btnnextmobile.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Mobile phones and tablets")
                        {
                            btnnextmobile.visibility = View.GONE
                        }
                        if(wapione.text == "Property")
                        {
                            btnnextproperty.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Property")
                        {
                            btnnextproperty.visibility = View.GONE
                        }
                        if(wapione.text == "Repair and Construction")
                        {
                            btnnextrepair.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Repair and Construction")
                        {
                            btnnextrepair.visibility = View.GONE
                        }
                        if(wapione.text == "Seeking work eg cv")
                        {
                            btnnextseeking.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Seeking work eg cv")
                        {
                            btnnextseeking.visibility = View.GONE
                        }
                        if(wapione.text == "Services")
                        {
                            btnnextservices.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Services")
                        {
                            btnnextservices.visibility = View.GONE
                        }
                        if(wapione.text == "Sports arts and outdoors")
                        {
                            btnnextsports.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Sports arts and outdoors")
                        {
                            btnnextsports.visibility = View.GONE
                        }
                        if(wapione.text == "Vehicles")
                        {
                            btnnextvehicles.visibility = View.VISIBLE
                        }
                        if(wapione.text != "Vehicles")
                        {
                            btnnextvehicles.visibility = View.GONE
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                    }
                }

            }

            btnnext.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,agricultureActivity::class.java))
            }
            btnnextanimals.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,animalsActivity::class.java))
            }
            btnnextbabies.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,babiesActivity::class.java))
            }
            btnnextequipment.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,equipmentActivity::class.java))
            }
            btnnextelectronics.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,electronicsActivity::class.java))
            }
            btnnextfashion.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,fashionActivity::class.java))
            }
            btnnexthealth.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,healthActivity::class.java))
            }
            btnnextfurniture.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,furnitureActivity::class.java))
            }


            btnnextjobs.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,jobsActivity::class.java))
            }
            btnnextmobile.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,mobileActivity::class.java))
            }
            btnnextproperty.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,propertyActivity::class.java))
            }
            btnnextrepair.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,repairActivity::class.java))
            }
            btnnextseeking.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,seekingActivity::class.java))
            }
            btnnextservices.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,servicesActivity::class.java))
            }
            btnnextsports.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,sportsActivity::class.java))
            }
            btnnextvehicles.setOnClickListener {
                val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                pref.putString("profileId",wapione.text.toString())
                pref.putString("storeid",storeid)
                pref.apply()
                startActivity(Intent(this,vehiclesActivity::class.java))
            }
        }






//

    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser == null)
        {

            minar.visibility = View.GONE
            minanyangor.visibility = View.VISIBLE

            bahatir.setOnClickListener {
                val modalbottomSheetFragment = CustomLoginFragment()
                this?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }

            }
            textView28r.setOnClickListener {
                val modalbottomSheetFragment = CustomSignupFragment()
                this?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }
            }

        }

    }



    }
