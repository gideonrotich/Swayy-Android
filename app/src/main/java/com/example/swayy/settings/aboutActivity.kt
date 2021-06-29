package com.example.swayy.settings

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.swayy.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_set.*
import kotlinx.android.synthetic.main.activity_set.smithrowb

class aboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)



        fun GetAppVersion(context: Context): String{
            var version = ""
            try {
                val pInfo = context.packageManager.getPackageInfo(context.packageName,0)
                version = pInfo.versionName
            } catch (e: PackageManager.NameNotFoundException){
                e.printStackTrace()
            }
            return version
        }

        val versionName = this?.let { GetAppVersion(it) }

        swat.text = "App Version " + versionName
    }
}