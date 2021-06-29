package com.example.swayy.categories

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_dogs.*
import kotlinx.android.synthetic.main.activity_yote.*

class yoteActivity : AppCompatActivity() {
    private var myUrl = ""
    private var imageUri: Uri? = null
    private var storageProfilePicRef: StorageReference? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var profileId:String
    private lateinit var nextId:String
    private lateinit var storeid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yote)

        val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()
            this.nextId = pref.getString("nextId","none").toString()
            this.storeid = pref.getString("storeid","none").toString()
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Picha yote bana")

        profile_image_settingsdogsyote.setOnClickListener {
            CropImage.activity()
                .setAspectRatio(1,1)
                .start(this@yoteActivity)
        }
        postfarmdogsyote.setOnClickListener {
            uploadImageAndUpdateInfo()
        }
    }

    private fun uploadImageAndUpdateInfo() {
        when{
            imageUri == null -> Toast.makeText(this,"Please select image first", Toast.LENGTH_LONG).show()

            etemailfarmdogsyote.text.toString() == "" -> {
                Toast.makeText(this,"Write title first", Toast.LENGTH_LONG).show()
            }
            etemailfarmlocation.text.toString() == "" -> {
                Toast.makeText(this,"Write the location first", Toast.LENGTH_LONG).show()
            }
            etfarmsubtypeyote.text.toString() == "" -> {
                Toast.makeText(this,"Write the  type first", Toast.LENGTH_LONG).show()
            }
            etfarmsubagecondition.text.toString() == "" -> {
                Toast.makeText(this,"Write the item condition first", Toast.LENGTH_LONG).show()
            }
            etfarmdescdogsyote.text.toString() == "" -> {
                Toast.makeText(this,"Write the description first", Toast.LENGTH_LONG).show()
            }
            etpricefarmdogsyote.text.toString() == "" -> {
                Toast.makeText(this,"Write the price first", Toast.LENGTH_LONG).show()
            }

            else -> {

                val progressDialog  = ProgressDialog(this)
                progressDialog.setTitle("Uploading Item")
                progressDialog.setMessage("Please wait,this might take a while...")
                progressDialog.show()
                val time= System.currentTimeMillis().toString()
                val fileRef  = storageProfilePicRef!!.child("$time.jpg")

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
                        val postId = ref.push().key

                        val userMap = HashMap<String, Any>()
                        userMap["category"] = profileId
                        userMap["subcategory"] = nextId
                        userMap["title"] = etemailfarmdogsyote.text.toString().toLowerCase()
                        userMap["location"] = etemailfarmlocation.text.toString().toLowerCase()
                        userMap["type"] = etfarmsubtypeyote.text.toString().toLowerCase()
                        userMap["condition"] = etfarmsubagecondition.text.toString().toLowerCase()
                        userMap["description"] = etfarmdescdogsyote.text.toString().toLowerCase()
                        userMap["price"] = etpricefarmdogsyote.text.toString().toLowerCase()
                        userMap["image"] = myUrl
                        userMap["uid"] = currentUserID
                        userMap["time"] = time
                        userMap["postid"] = postId!!

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
            profile_image_settingsdogsyote.setImageURI(imageUri)

        }

    }
}