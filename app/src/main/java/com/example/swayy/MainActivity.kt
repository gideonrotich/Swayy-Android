package com.example.swayy

import android.content.Intent
import android.os.Build
import android.os.Bundle
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
        val bottomNavigation: BottomNavigationView = findViewById(R.id.btn_nav)

        val window: Window = this@MainActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.mkalee)

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