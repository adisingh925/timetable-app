package com.example.navigation

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class user : Fragment() {

    var db = Firebase.firestore
    var auth = Firebase.auth

    val storageref = FirebaseStorage.getInstance().reference

    lateinit var uri:Uri

    lateinit var uuri:String

    lateinit var imageview:ImageView

    lateinit var imageview1:ImageView

    lateinit var king:String

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

        imageview = view?.findViewById<ImageView>(R.id.imageview)!!

        /////////////////



        /////////////////


        view?.findViewById<Button>(R.id.button3)?.setOnClickListener()
        {
            auth.signOut()
            val inte = Intent(this@user.context,loginactivity::class.java)
            startActivity(inte)
        }

        val z = db.collection("user data").document("user data").collection(globalname)
            .document("login credentials")
        z.get().addOnSuccessListener { document ->
            if (document.exists()) {
                if (y != null) {
                    y.text = document.getString("username")

                }
                if (hh != null) {
                    hh.text = document.getString("email")
                }

                val uuri = document.getString("imgpath")

                Glide
                    .with(this)
                    .load(uuri)
                    .circleCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(imageview)
            }
        }

        val ff = view?.findViewById<TextView>(R.id.textview3)
        if (ff != null) {
            ff.text = globalname
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

                var syst = System.currentTimeMillis()
                var imgref = storageref.child("uploads/$syst.pdf")
                imgref.putFile(uri).addOnSuccessListener()
                {
                    Toast.makeText(this@user.context,"Upload Success",Toast.LENGTH_SHORT).show()

                    storageref.child("uploads/$syst.pdf").downloadUrl.addOnSuccessListener()
                    {
                            url ->
                        var hmp = hashMapOf("imgpath" to url.toString())

                        Glide
                            .with(this)
                            .load(url.toString())
                            .circleCrop()
                            .placeholder(R.drawable.placeholder)
                            .into(imageview)


                        db.collection("user data").document("user data").collection(globalname)
                            .document("login credentials").set(hmp, SetOptions.merge())

                    }
                }
                    .addOnFailureListener()
                    {
                        Toast.makeText(this@user.context,"Upload Failed",Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

}

