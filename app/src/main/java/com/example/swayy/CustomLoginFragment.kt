package com.example.swayy

import android.app.ProgressDialog
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
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.view.*

class CustomLoginFragment: BottomSheetDialogFragment() {
    private var mUser:MutableList<User>? = null
    private var userAdapter: UserAdapter? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var userid:String

    companion object {
        const val TAG = "CustomLoginFragment"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.activity_signup,container,false)

        view.textView28.setOnClickListener {
            val modalbottomSheetFragment = CustomSignupFragment()
            activity?.let { it1 -> modalbottomSheetFragment.show(it1.supportFragmentManager,modalbottomSheetFragment.tag) }
        }

        view.btnSignIn.setOnClickListener {
            val email = view.etEmaill.text.toString()
            val password = view.etPasswordd.text.toString()

            when {
                TextUtils.isEmpty(email) -> Toast.makeText(context,"Email is required", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(password) -> Toast.makeText(context,"Password is required", Toast.LENGTH_LONG).show()


                else -> {
                    val progressDialog = ProgressDialog(context)
                    progressDialog.setTitle("Sign In")
                    progressDialog.setMessage("Please wait, this might take a while...")
                    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
                        if (task.isSuccessful)
                        {
                            progressDialog.dismiss()
                            val intent = Intent(context,MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        }
                        else
                        {
                            view.textView96.visibility = View.VISIBLE
                            view.textView9.visibility = View.GONE
                            FirebaseAuth.getInstance().signOut()
                            progressDialog.dismiss()
                        }
                    }

                }
            }
        }


        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}