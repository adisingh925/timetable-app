package com.example.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.concurrent.fixedRateTimer


class user : Fragment() {

    var db = Firebase.firestore
    var auth = Firebase.auth


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

        val z = db.collection("user data").document("user data").collection(auth.uid.toString()).document("login credentials")
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

        val imageview = view?.findViewById<ImageView>(R.id.imageview)

        if (imageview != null) {
            Glide
                .with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/timetable-af218.appspot.com/o/WhatsApp%20Image%202021-06-20%20at%207.22.46%20PM.jpeg?alt=media&token=5f059c79-ec20-427f-9ca0-101767f3f615")
                .circleCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(imageview)
        };

    }

    }