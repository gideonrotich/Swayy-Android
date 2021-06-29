package com.example.swayy.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.swayy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.activity_profile.*

class feedbackActivity : AppCompatActivity() {
    private lateinit var firebaseUser: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        btnSignInfacebookfood.setOnClickListener {
            sendFeedback()
        }
    }

    private fun sendFeedback() {
        when{
            etEmailfeed.text.toString() == "" -> {
                Toast.makeText(this,"Write feedback first", Toast.LENGTH_LONG).show()
            }
            etEmailfood.text.toString() == "" -> {
                Toast.makeText(this,"Write email first", Toast.LENGTH_LONG).show()
            }
            else ->{
                val jobsRef = FirebaseDatabase.getInstance().reference.child("Feedback").child(firebaseUser.uid)

                val userMap = HashMap<String, Any>()
                userMap["feedback"] = etEmailfeed.text.toString().toLowerCase()
                userMap["email"] = etEmailfood.text.toString().toLowerCase()


                jobsRef.setValue(userMap)

                Toast.makeText(this,"Feedback sent successfully", Toast.LENGTH_LONG).show()
            }
        }
    }
}