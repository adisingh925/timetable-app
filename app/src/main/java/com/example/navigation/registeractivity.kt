package com.example.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class registeractivity : AppCompatActivity()
{
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeractivity)

        auth = Firebase.auth

        val db = Firebase.firestore

        val email1 = findViewById<EditText>(R.id.editTextTextPersonName)

        val password1 = findViewById<EditText>(R.id.editTextTextPassword)

        val username = findViewById<EditText>(R.id.editTextTextPersonName3)

        val register = findViewById<Button>(R.id.button7)

        var value = 0

        val tick = findViewById<ImageView>(R.id.imageView2)

        val cross = findViewById<ImageView>(R.id.imageView3)

        val icon = findViewById<ImageView>(R.id.imageView4)

        tick.isVisible = false
        cross.isVisible = false

        var list = mutableListOf<String>()

        Glide.with(this).load(R.mipmap.ic_launcher_foreground).circleCrop().into(icon)


        var kingu =  db.collection("user data").document("user data").collection("usernames").document("usernames")

        kingu.get().addOnSuccessListener()
        {
            document->

            if(document.exists())
            {
                var vv = document.getString("value")

                var rs = vv?.toInt()

                if (rs != null) {
                    value = rs
                }

                for(i in 1..value)
                {
                        list.add(document.getString("username$i")!!)
                }
            }
        }

        username.doOnTextChanged { text, start, before, count ->

                var namu = username.text.toString()
                    if(namu in list) {
                        tick.isVisible = false
                        cross.isVisible = true
                    } else {
                        tick.isVisible = true
                        cross.isVisible = false
                    }

        }

        register.setOnClickListener()
        {
            var x =0
            if(email1.text.toString().isEmpty() || password1.text.toString().isEmpty() || username.text.toString().isEmpty())
            {
                Toast.makeText(
                    this@registeractivity, "Please Enter Value for all Fields",
                    Toast.LENGTH_SHORT
                ).show()

                x++
            }

            if(cross.isVisible)
            {
                x++
                Toast.makeText(
                    this@registeractivity, "Username already exists",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if(username.text.toString().contains("."))
            {
                username.text.toString().replace(".",",")
            }

            if(x==0) {

                auth.createUserWithEmailAndPassword(
                    email1.text.toString(),
                    password1.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            val data = hashMapOf("username" to username.text.toString(), "email" to email1.text.toString(), "password" to password1.text.toString(), "status" to "false")
                            db.collection("user data").document("user data").collection(username.text.toString()).document("login credentials").set(data)

                            value++

                            val usnames = hashMapOf("username$value" to username.text.toString())

                            db.collection("user data").document("user data").collection("usernames").document("usernames").set(usnames,
                                SetOptions.merge())

                            val vall = hashMapOf("value" to value.toString())

                            db.collection("user data").document("user data").collection("usernames").document("usernames").set(vall,
                                SetOptions.merge())

                            Toast.makeText(
                                this@registeractivity, "Authentication successful.",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@registeractivity, loginactivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@registeractivity, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}