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
import kotlinx.android.synthetic.main.activity_agriculture.*
import kotlinx.android.synthetic.main.activity_farm.*

class farmActivity : AppCompatActivity() {
    private var myUrl = ""
    private var imageUri: Uri? = null
    private var storageProfilePicRef: StorageReference? = null
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var profileId:String
    private lateinit var nextId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farm)
        val pref = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null)
        {
            this.profileId = pref.getString("profileId","none").toString()
            this.nextId = pref.getString("nextId","none").toString()
        }


        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Farm machinery and equipment Pictures")



        change_image.setOnClickListener {
            CropImage.activity()
                .setAspectRatio(1,1)
                .start(this@farmActivity)
        }
        postfarm.setOnClickListener {
            uploadImageAndUpdateInfo()
        }







        val spinnertwo = findViewById<Spinner>(R.id.typefarm)
        if (spinnertwo != null) {

            val electronics = resources.getStringArray(R.array.typefarm)
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
                    tpespinfarm.text = me

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
        val spinnerthree = findViewById<Spinner>(R.id.conditionfarm)
        if (spinnerthree != null) {

            val electronics = resources.getStringArray(R.array.conditionfarm)
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, electronics
            )
            spinnerthree.adapter = adapter
            spinnerthree.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    tpespinfarmtext.text = me

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
    }

    private fun uploadImageAndUpdateInfo() {
        when{
            imageUri == null -> Toast.makeText(this,"Please select image first",Toast.LENGTH_LONG).show()

            etemailfarm.text.toString() == "" -> {
                Toast.makeText(this,"Write title first", Toast.LENGTH_LONG).show()
            }
            etfarmdesc.text.toString() == "" -> {
                Toast.makeText(this,"Write the description first", Toast.LENGTH_LONG).show()
            }
            etpricefarm.text.toString() == "" -> {
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
                        userMap["title"] = etemailfarm.text.toString().toLowerCase()
                        userMap["description"] = etfarmdesc.text.toString().toLowerCase()
                        userMap["price"] = etpricefarm.text.toString().toLowerCase()
                        userMap["type"] = tpespinfarm.text.toString().toLowerCase()
                        userMap["condition"] = tpespinfarmtext.text.toString().toLowerCase()
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
            profile_image_settings.setImageURI(imageUri)

        }

    }



}