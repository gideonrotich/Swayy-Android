package com.example.swayy

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var homeFragment:homeFragment
    lateinit var favoriteFragment: favoriteFragment
    lateinit var sellFragment:sellFragment
    lateinit var notificationsFragment:notificationsFragment
    lateinit var profileFragment:profileFragment


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //init others
        val bottomNavigation: BottomNavigationView = findViewById(R.id.btn_nav)

        homeFragment = homeFragment()
        supportFragmentManager
            .beginTransaction().replace(R.id.frame_layout, homeFragment).setTransition(
                FragmentTransaction.TRANSIT_FRAGMENT_OPEN
            ).commit()

//    }

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.navigation_home -> {
                    homeFragment = homeFragment()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout, homeFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }

                R.id.navigation_dashboard -> {
                    favoriteFragment = favoriteFragment()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout, favoriteFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }

                R.id.navigation_sell -> {

                        startActivity(Intent(this, ItemsaleActivity::class.java))

                 }

                R.id.navigation_not -> {
                    notificationsFragment = notificationsFragment()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout, notificationsFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }

                R.id.navigation_profile -> {
                    profileFragment = profileFragment()
                    supportFragmentManager
                        .beginTransaction().replace(R.id.frame_layout, profileFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                }
            }
            true
        }



    }

}