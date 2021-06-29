package com.example.swayy.stores

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
import kotlinx.android.synthetic.main.activity_stores.*
import kotlinx.android.synthetic.main.activity_yote.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class storesActivity : AppCompatActivity() {
    private var myUrl = ""
    private var imageUri: Uri? = null
    private var storageProfilePicRef: StorageReference? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var kujaid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stores)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Store logo pictures")

        val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.kujaid = pref.getString("kujaid","none").toString()

        }

        change_image_dogsyotenum.setOnClickListener {
            CropImage.activity()
                .setAspectRatio(1,1)
                .start(this@storesActivity)
        }
        postfarmdogsyotenum.setOnClickListener {
            uploadImageAndUpdateInfo()
        }




    }

    private fun uploadImageAndUpdateInfo() {
        when{
            imageUri == null -> Toast.makeText(this,"Please select image first", Toast.LENGTH_LONG).show()

            mwendoname.text.toString() == "" -> {
                Toast.makeText(this,"Write title first", Toast.LENGTH_LONG).show()
            }
            kimbianame.text.toString() == "" -> {
                Toast.makeText(this,"Write the description first", Toast.LENGTH_LONG).show()
            }
            sasaname.text.toString() == "" -> {
                Toast.makeText(this,"Write the  location first", Toast.LENGTH_LONG).show()
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
                        val timeformat = SimpleDateFormat("dd/M/yyyy")
                        val date:Date = Date()
                        val strDate = timeformat.format(date).toString()
                        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
                        val ref  = FirebaseDatabase.getInstance().reference.child(kujaid).child("Stores")
                        val storeId = ref.push().key

                        val userMap = HashMap<String, Any>()
                        userMap["title"] = mwendoname.text.toString().toLowerCase()
                        userMap["description"] = kimbianame.text.toString().toLowerCase()
                        userMap["website"] = webname.text.toString().toLowerCase()
                        userMap["location"] = sasaname.text.toString().toLowerCase()
                        userMap["category"] = kujaid
                        userMap["image"] = myUrl
                        userMap["uid"] = currentUserID
                        userMap["time"] = strDate
                        userMap["storeid"] = storeId!!

                        ref.child(currentUserID).setValue(userMap)

                        Toast.makeText(this,"Your Shop has been posted successfully", Toast.LENGTH_LONG).show()

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
            profile_image_settingsdogsyotename.setImageURI(imageUri)

        }

    }
}