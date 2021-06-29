package com.example.swayy.stores

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.swayy.MainActivity
import com.example.swayy.R
import com.example.swayy.model.Store
import com.example.swayy.model.User
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_storedetail.view.*

class settingsActivity : AppCompatActivity() {
    private lateinit var ndan:String
    private lateinit var mat:String
    private lateinit var firebaseUser: FirebaseUser
    private var checker = ""
    private var myUrl = ""
    private var imageUri: Uri? = null
    private var storageProfilePicRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Store logo pictures")

        val pref = this?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)



        if (pref != null)
        {
            this.ndan = pref.getString("ndan","none").toString()
            this.mat = pref.getString("mat","none").toString()

        }
        btntoa.setOnClickListener {
            val eBuilder = AlertDialog.Builder(this)
            eBuilder.setCancelable(false)

            eBuilder.setMessage("Are you sure you want to delete this Store?")
            eBuilder.setPositiveButton("Yes")


            {
                    Dialog,which->
                val ref = FirebaseDatabase.getInstance().reference.child(ndan).child("Stores").child(mat)
                ref.removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful)
                    {
                        Toast.makeText(this,"Store deleted successfully..", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,MainActivity::class.java))


                    }

                    if (task.isCanceled)
                    {
                        Toast.makeText(this,"Something went wrong...Try again later", Toast.LENGTH_LONG).show()
                    }
                }
            }
            eBuilder.setNegativeButton("No")
            {
                    Dialog,which ->


            }
            val createBuild  = eBuilder.create()
            createBuild.show()

        }

        val jobsRef = FirebaseDatabase.getInstance().getReference().child(ndan).child("Stores").child(mat)

        jobsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists())
                {
                    val user = p0.getValue<Store>(Store::class.java)

                    Glide.with(this@settingsActivity!!)  //2
                        .load(user!!.getImage()) //3
                        .centerCrop() //4
                        .into(pichakampunioo)
                    etFirstNameonesh.setText(user.getTitle())
                    etFirstNameoneshdes.setText(user!!.getDescription())
                    etFirstNameoneshloc.setText(user!!.getLocation())
                    etFirstNameoneshweb.setText(user!!.getWebsite())

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



        badilisha.setOnClickListener {
            checker = "clicked"
            CropImage.activity()
                .setAspectRatio(1,1)
                .start(this@settingsActivity)
        }

        tz.setOnClickListener {
            if (checker == "clicked")
            {

                when{
                    imageUri == null -> Toast.makeText(this,"Please select image first",Toast.LENGTH_LONG).show()

                    etFirstNameonesh.text.toString() == "" -> {
                        Toast.makeText(this,"Write shop title first", Toast.LENGTH_LONG).show()
                    }
                    etFirstNameoneshweb.text.toString() == "" -> {
                        Toast.makeText(this,"Write shop website first", Toast.LENGTH_LONG).show()
                    }
                    etFirstNameoneshloc.text.toString() == "" -> {
                        Toast.makeText(this,"Write shop location first", Toast.LENGTH_LONG).show()
                    }
                    etFirstNameoneshdes.text.toString() == "" -> {
                        Toast.makeText(this,"Write shop description first", Toast.LENGTH_LONG).show()
                    }

                    else -> {

                        val progressDialog  = ProgressDialog(this)
                        progressDialog.setTitle("Shop Settings")
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

                                val ref  = FirebaseDatabase.getInstance().reference.child(ndan).child("Stores")

                                val userMap = HashMap<String, Any>()
                                userMap["title"] = etFirstNameonesh.text.toString().toLowerCase()
                                userMap["description"] = etFirstNameoneshdes.text.toString().toLowerCase()
                                userMap["location"] = etFirstNameoneshloc.text.toString()
                                userMap["website"] = etFirstNameoneshweb.text.toString()
                                userMap["image"] = myUrl

                                ref.child(mat).updateChildren(userMap)

                                Toast.makeText(this,"Shop has been updated successfully", Toast.LENGTH_LONG).show()

                                val intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                                progressDialog.dismiss()
                            }
                            else
                            {
                                progressDialog.dismiss()
                            }
                        } )

//                else below
                    }


                }
            }
            else
            {
                when {
                    etFirstNameonesh.text.toString() == "" -> {
                        Toast.makeText(this,"Write shop title first", Toast.LENGTH_LONG).show()
                    }
                    etFirstNameoneshweb.text.toString() == "" -> {
                        Toast.makeText(this,"Write shop website first", Toast.LENGTH_LONG).show()
                    }
                    etFirstNameoneshloc.text.toString() == "" -> {
                        Toast.makeText(this,"Write shop location first", Toast.LENGTH_LONG).show()
                    }
                    etFirstNameoneshdes.text.toString() == "" -> {
                        Toast.makeText(this,"Write shop description first", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        val jobsRef = FirebaseDatabase.getInstance().reference.child(ndan).child("Stores")

                        val userMap = HashMap<String, Any>()
                        userMap["title"] = etFirstNameonesh.text.toString().toLowerCase()
                        userMap["description"] = etFirstNameoneshdes.text.toString().toLowerCase()
                        userMap["location"] = etFirstNameoneshloc.text.toString()
                        userMap["website"] = etFirstNameoneshweb.text.toString()

                        jobsRef.child(mat).updateChildren(userMap)

                        Toast.makeText(this,"Shop has been updated successfully", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    }
                }
            }

        }

    }




    private fun userInfo() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data!= null)
        {
            val result = CropImage.getActivityResult(data)
            imageUri = result.uri
            pichakampunioo.setImageURI(imageUri)

        }

    }
}