package com.example.swayy

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME: Long = 4000
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val window: Window = this@SplashScreen.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@SplashScreen,R.color.giddy)

            if (FirebaseAuth.getInstance().currentUser == null)
            {

                startActivity(Intent(this,SignupActivity::class.java))

            }
            else {
                Handler().postDelayed({

                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }, SPLASH_TIME)
            }


        }

        override fun onStart() {
            super.onStart()

            if (FirebaseAuth.getInstance().currentUser == null)
            {
                val intent = Intent(this@SplashScreen,SignupActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }


}