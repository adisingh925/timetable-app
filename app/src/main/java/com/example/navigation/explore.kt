package com.example.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class explore : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        val back = findViewById<ImageView>(R.id.imageview)

        val usernames = mutableListOf<exploredataclass>()

        var rcv1 = exploreadapterclass(usernames)

        var rcv = findViewById<RecyclerView>(R.id.recyclerview)

        rcv.adapter = rcv1

        rcv.layoutManager = LinearLayoutManager(this)

        val x = db.collection("user data").document("user data").collection("usernames").document("usernames")

        x.get().addOnSuccessListener()
        {
            document->

            if(document.exists())
            {
                var value = document.getString("value")?.toInt()

                for(i in 1..value!!)
                {
                    var names = document.getString("username$i")
                    var imagepath = names?.let { document.getString(it) }
                    usernames.add(exploredataclass(names!!, imagepath!!))
                    rcv1.notifyDataSetChanged()
                }
            }
        }

        back.setOnClickListener()
        {
            finish()
        }
    }
}