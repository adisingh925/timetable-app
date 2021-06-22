package com.example.navigation

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.net.URI
import java.security.Permission
import kotlin.concurrent.fixedRateTimer


class user : Fragment() {

    var db = Firebase.firestore
    var auth = Firebase.auth

    lateinit var uri:Uri

    lateinit var imageview:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onStart() {
        super.onStart()
        val x = view?.findViewById<TextView>(R.id.textview)


        val y = view?.findViewById<TextView>(R.id.textview1)
        val hh = view?.findViewById<TextView>(R.id.textview5)

        val z = db.collection("user data").document("user data").collection(auth.uid.toString())
            .document("login credentials")
        z.get().addOnSuccessListener { document ->
            if (document.exists()) {
                if (y != null) {
                    y.text = document.getString("username")

                }
                if (hh != null) {
                    hh.text = document.getString("email")
                }
            }
        }

        val ff = view?.findViewById<TextView>(R.id.textview3)
        if (ff != null) {
            ff.text = auth.uid.toString()
        }

        imageview = view?.findViewById<ImageView>(R.id.imageview)!!

        if (imageview != null) {
            Glide
                .with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/timetable-af218.appspot.com/o/WhatsApp%20Image%202021-06-20%20at%207.22.46%20PM.jpeg?alt=media&token=5f059c79-ec20-427f-9ca0-101767f3f615")
                .circleCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(imageview)
        }

        imageview?.setOnClickListener()
        {
            Dexter.withContext(imageview.context)
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        val intent = Intent()
                        intent.setType("image/*")
                        intent.setAction(Intent.ACTION_PICK)
                        startActivityForResult(Intent.createChooser(intent, "select image"), 121)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        val builder = AlertDialog.Builder(this@user.context)
                        builder.setTitle("Need Permission")
                            .setMessage("This app needs permission to use this feature. You can grant them in app settings.")
                            .setPositiveButton("go to settings", DialogInterface.OnClickListener
                            { dialog, id ->

                                val inte = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", context?.packageName, null)
                                inte.setData(uri)
                                startActivityForResult(inte, 102)

                            })
                            .setNegativeButton(
                                "cancel",
                                DialogInterface.OnClickListener { dialog, which ->

                                    dialog.cancel()
                                })

                        builder.show()

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

        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                uri = data.data!!
            }
            if (imageview != null) {
                Glide
                    .with(this)
                    .load(uri.toString())
                    .circleCrop()
                    .placeholder(R.drawable.image_placeholder)
                    .into(imageview)
            }
        }

    }
}

