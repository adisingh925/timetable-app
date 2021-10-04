package com.app1.navigation

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
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
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

        val name = view?.findViewById<TextView>(R.id.textview1)
        val email = view?.findViewById<TextView>(R.id.textview5)

        imageview = view?.findViewById(R.id.imageview)!!

        val z = db.collection("userdata").document("userdata").collection(auth.uid!!)
            .document("login credentials")

        z.get().addOnSuccessListener { document ->
            if (document.exists()) {
                if (name != null) {
                    name.text = document.getString("name")

                }
                if (email != null) {
                    email.text = document.getString("email")
                }

                val url1 = document.getString("imgpath")

                if(url1 == null)
                {
                    Glide
                        .with(this)
                        .load("https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/User_icon_2.svg/220px-User_icon_2.svg.png")
                        .circleCrop()
                        .into(imageview)
                }
                else
                {
                    Glide
                        .with(this)
                        .load(url1)
                        .circleCrop()
                        .into(imageview)
                }
            }
        }

        val userid = view?.findViewById<TextView>(R.id.textview3)
        if (userid != null) {
            userid.text = auth.uid!!
        }

        imageview?.setOnClickListener()
        {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(intent, "select image"), 121)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                uri = data.data!!

                var imgref = storageref.child("$auth.uid!!/profile_pic.pdf")

                imgref.putFile(uri).addOnSuccessListener()
                {
                    Toast.makeText(this@user.context,"Upload Success",Toast.LENGTH_SHORT).show()

                    storageref.child("$auth.uid!!/profile_pic.pdf").downloadUrl.addOnSuccessListener()
                    {
                            url ->

                        var hmp = hashMapOf("imgpath" to url.toString())

                        Glide
                            .with(this)
                            .load(url)
                            .circleCrop()
                            .into(imageview)

                        db.collection("userdata").document("userdata").collection(auth.uid!!)
                            .document("login credentials").set(hmp, SetOptions.merge())

                        val ppt = hashMapOf(auth.uid!! to url.toString())

                        db.collection("userdata").document("userdata").collection("usernames")
                            .document("usernames").set(ppt, SetOptions.merge())

                    }
                }.addOnFailureListener()
                {
                    Toast.makeText(this@user.context,"Upload Failed",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}

