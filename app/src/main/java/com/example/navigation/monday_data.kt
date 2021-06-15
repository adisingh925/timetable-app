package com.example.navigation

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.storage.StorageManager
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.karumi.dexter.Dexter.*
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.grpc.Context
import java.io.File
import java.io.InputStream
import java.util.jar.Manifest


class monday_data : AppCompatActivity() {

    val storage = Firebase.storage



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monday_data)

        val back = findViewById<ImageButton>(R.id.imageview)

        val upload = findViewById<FloatingActionButton>(R.id.floatingActionButton2)

        back.setOnClickListener()
        {
            finish()
        }

        upload.setOnClickListener()
        {
            withContext(this)
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse)
                    {
                        val intent = Intent()
                        intent.setType("image/*")
                        intent.setAction(Intent.ACTION_GET_CONTENT)
                        startActivityForResult(Intent.createChooser(intent,"select files"),12)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse)
                    {

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?,
                    ) {
                        token?.continuePermissionRequest()
                    }
                }).check()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == 12 && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            var imageview = findViewById<ImageView>(R.id.imageView)
            val inputstream = contentResolver.openInputStream(data.getData()!!)
            val bitmap = BitmapFactory.decodeStream(inputstream)
            imageview.setImageBitmap(bitmap)

            val pdf:Uri = data.getData()!!
            val storageref = FirebaseStorage.getInstance().getReference()

            val filepath = storageref.child("image1").putFile(pdf)
            filepath.addOnSuccessListener()
            {
                Toast.makeText(this,"file upload success",Toast.LENGTH_SHORT)
            }
                .addOnFailureListener()
                {
                    Toast.makeText(this,"file upload failed",Toast.LENGTH_SHORT)
                }
        }
    }
}