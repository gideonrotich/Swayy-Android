package com.example.swayy

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.components.Dependency.required
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)



        textView30.setOnClickListener { startActivity(Intent(this, SignupActivity::class.java))
             }



        btnSignup.setOnClickListener {
            CreateAccount()
            isValid()
        }
    }



    private fun CreateAccount() {
        val fullname = etFirstName.text.toString()
        val email = etEmail.text.toString()
        val mobile = etLastName.text.toString()
        val password = etPassword.text.toString()


        when{
//            TextUtils.isEmpty(fullname) -> Toast.makeText(this,"FullName is required", Toast.LENGTH_LONG).show()
//            TextUtils.isEmpty(email) -> Toast.makeText(this,"Email is required", Toast.LENGTH_LONG).show()
//            TextUtils.isEmpty(mobile) -> Toast.makeText(this,"Job Category is required", Toast.LENGTH_LONG).show()
//            TextUtils.isEmpty(password) -> Toast.makeText(this,"Password is required", Toast.LENGTH_LONG).show()

            else -> {

                val progressDialog = ProgressDialog(this@SigninActivity)
                progressDialog.setTitle("Sign Up")
                progressDialog.setMessage("Please wait, this might take a while...")
                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()


                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                        {

                            saveUserInfo(fullname,email,mobile,progressDialog)
                        }
                        else
                        {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private fun saveUserInfo(fullname: String, email: String, mobile: String, progressDialog: ProgressDialog) {

        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val timeformat = SimpleDateFormat("dd/M/yyyy")
        val date: Date = Date()
        val strDate = timeformat.format(date).toString()
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String, Any>()
        userMap["fullname"] = fullname
        userMap["email"] = email
        userMap["mobile"] = mobile
        userMap["uid"] = currentUserID
        userMap["time"] = strDate
        userMap["image"] = "gs://jobstock-17213.appspot.com/Default Images/person.png"


        usersRef.child(currentUserID).setValue(userMap)

            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {


                    progressDialog.dismiss()

                    val intent = Intent(this@SigninActivity,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
//                    naneanimationme.visibility = View.VISIBLE
//                    CoroutineScope(Dispatchers.Main).launch {
//                        delay(5500L)
                        startActivity(Intent(this@SigninActivity,MainActivity::class.java))
//                    }
                    finish()

                }
                else
                {
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "Error: $message",Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }
    }



    private fun isValid(): Boolean {
        var isValid = true

        val justJavaEmailPattern = Pattern.compile("^([a-zA-Z0-9_.-])+@justjava.com+")
        val justJavaEmailMatcher = justJavaEmailPattern.matcher(etEmail.toString())

        if (etEmail.toString().isEmpty()) {
            tilEmail.error = getString(R.string.required)
            isValid = false
        } else if (justJavaEmailMatcher.matches()) {
            tilEmail.error = "Can not use @justjava.com"
            isValid = false
        }

        if (etPassword.toString().isEmpty()) {
            tilPassword.error = getString(R.string.required)
            isValid = false
        } else if (etPassword.toString().length < 6) {
            tilPassword.error = "At least 6 characters"
            isValid = false
        }

        if (etFirstName.toString().isEmpty()) {
            tilFirstName.error = getString(R.string.required)
            isValid = false
        }

        if (etLastName.toString().isEmpty()) {
            tilLastName.error = getString(R.string.required)
            isValid = false
        }

        return isValid
    }
}