package com.example.swayy.categories

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.swayy.MainActivity
import com.example.swayy.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_livestock.*
import kotlinx.android.synthetic.main.activity_meals.*

class mealsActivity : AppCompatActivity() {
    private var myUrl = ""
    private var imageUri: Uri? = null
    private var storageProfilePicRef: StorageReference? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var profileId:String
    private lateinit var nextId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)

        val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()
            this.nextId = pref.getString("nextId","none").toString()
        }


        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Meals and drinks Pictures")



        change_image_meals.setOnClickListener {
            CropImage.activity()
                .setAspectRatio(1,1)
                .start(this@mealsActivity)
        }
        postfarmmeals.setOnClickListener {
            uploadImageAndUpdateInfo()
        }

        val spinnertwo = findViewById<Spinner>(R.id.typefarmmeals)
        if (spinnertwo != null) {

            val electronics = resources.getStringArray(R.array.typemeals)
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, electronics
            )
            spinnertwo.adapter = adapter
            spinnertwo.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    tpespinfarmmeals.text = me

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
    }

    private fun uploadImageAndUpdateInfo() {
        when{
            imageUri == null -> Toast.makeText(this,"Please select image first", Toast.LENGTH_LONG).show()

            etemailfarmmeals.text.toString() == "" -> {
                Toast.makeText(this,"Write title first", Toast.LENGTH_LONG).show()
            }
            etfarmsublivestock.text.toString() == "" -> {
                Toast.makeText(this,"Write the sub type first", Toast.LENGTH_LONG).show()
            }
            etfarmdescmeals.text.toString() == "" -> {
                Toast.makeText(this,"Write the description first", Toast.LENGTH_LONG).show()
            }
            etpricefarmmeals.text.toString() == "" -> {
                Toast.makeText(this,"Write the price first", Toast.LENGTH_LONG).show()
            }

            else -> {

                val progressDialog  = ProgressDialog(this)
                progressDialog.setTitle("Uploading Item")
                progressDialog.setMessage("Please wait,this might take a while...")
                progressDialog.show()

                val fileRef  = storageProfilePicRef!!.child(firebaseUser!!.uid + ".jpg")

                var uploadTask: StorageTask<*>
                uploadTask = fileRef.putFile(imageUri!!)
                uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                    if (!task.isSuccessful)
                    {
                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                }).addOnCompleteListener (OnCompleteListener<Uri> { task ->
                    if (task.isSuccessful)
                    {
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()
                        val time= System.currentTimeMillis().toString()
                        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
                        val ref  = FirebaseDatabase.getInstance().reference.child(profileId).child(nextId)

                        val userMap = HashMap<String, Any>()
                        userMap["category"] = profileId
                        userMap["subcategory"] = nextId
                        userMap["title"] = etemailfarmmeals.text.toString().toLowerCase()
                        userMap["subtype"] = etfarmsublivestock.text.toString().toLowerCase()
                        userMap["description"] = etfarmdescmeals.text.toString().toLowerCase()
                        userMap["price"] = etpricefarmmeals.text.toString().toLowerCase()
                        userMap["type"] = tpespinfarmmeals.text.toString().toLowerCase()
                        userMap["image"] = myUrl
                        userMap["uid"] = currentUserID
                        userMap["time"] = time

                        ref.child(time).setValue(userMap)

                        Toast.makeText(this,"Your item has been posted successfully", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        progressDialog.dismiss()
                    }
                    else
                    {
                        progressDialog.dismiss()
                    }
                } )


            }


        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data!= null)
        {
            val result = CropImage.getActivityResult(data)
            imageUri = result.uri
            profile_image_settingsmeals.setImageURI(imageUri)

        }

    }
}