package com.app1.navigation

import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class settings : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val back = findViewById<ImageView>(R.id.imageview)

        val switch = findViewById<Switch>(R.id.switch1)

        val x = db.collection("user data").document("user data").collection("usernames").document("usernames")

        x.get().addOnSuccessListener()
        {
            document->

            switch.isChecked = document.getString("${globalname}status") != "false"
        }

        switch.setOnClickListener()
        {
            if (switch.isChecked) {
                var update = hashMapOf("${globalname}status" to "true")
                x.set(update, SetOptions.merge())
            } else {
                var update = hashMapOf("${globalname}status" to "false")
                x.set(update, SetOptions.merge())
            }
        }


        back.setOnClickListener()
        {
            finish()
        }
    }
}