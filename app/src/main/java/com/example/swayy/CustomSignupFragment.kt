package com.example.swayy

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.swayy.Adapter.UserAdapter
import com.example.swayy.model.User
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signin.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CustomSignupFragment: BottomSheetDialogFragment()  {
    private var mUser:MutableList<User>? = null
    private var userAdapter: UserAdapter? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var userid:String

    companion object {
        const val TAG = "CustomSignupFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.activity_signin,container,false)

        view.textView30.setOnClickListener {
            val modalbottomSheetFragment = CustomLoginFragment()
            activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }
        }

        view.btnSignup.setOnClickListener {
            val fullname = view.etFirstName.text.toString()
            val email = view.etEmail.text.toString()
            val mobile = view.etLastName.text.toString()
            val password = view.etPassword.text.toString()


            when{
            TextUtils.isEmpty(fullname) -> Toast.makeText(context,"FullName is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(context,"Email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(mobile) -> Toast.makeText(context,"Job Category is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(context,"Password is required", Toast.LENGTH_LONG).show()

                else -> {

                    val progressDialog = ProgressDialog(context)
                    progressDialog.setTitle("Sign Up")
                    progressDialog.setMessage("Please wait, this might take a while...")
                    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()


                    mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful)
                            {
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

                                            val intent = Intent(context,MainActivity::class.java)
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                            startActivity(intent)
//                    naneanimationme.visibility = View.VISIBLE
//                    CoroutineScope(Dispatchers.Main).launch {
//                        delay(5500L)
                                            startActivity(Intent(context,MainActivity::class.java))
//                    }


                                        }
                                        else
                                        {
                                            val message = task.exception!!.toString()
                                            Toast.makeText(context, "Error: $message",Toast.LENGTH_LONG).show()
                                            FirebaseAuth.getInstance().signOut()
                                            progressDialog.dismiss()
                                        }
                                    }
                            }
                            else
                            {
                                val message = task.exception!!.toString()
                                Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
                                mAuth.signOut()
                                progressDialog.dismiss()
                            }
                        }
                }
            }
        }

        return view
    }

}