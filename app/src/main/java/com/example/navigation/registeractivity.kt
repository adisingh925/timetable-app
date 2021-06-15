package com.example.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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

        val signin = findViewById<Chip>(R.id.chip4)

        val confi = "[0-9a-zA-Z]+@[a-zA-Z]+.com".toRegex()

        signin.setOnClickListener()
        {
            finish()
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

            if(!email1.text.toString().matches(confi) && x==0)
            {
                Toast.makeText(
                    this@registeractivity, "Invalid Email Format",
                    Toast.LENGTH_SHORT
                ).show()

                x++
            }


            if(x==0) {

                auth.createUserWithEmailAndPassword(
                    email1.text.toString(),
                    password1.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            val data = hashMapOf("username" to username.text.toString(), "email" to email1.text.toString(), "password" to password1.text.toString(), "status" to "false")
                            db.collection(auth.uid.toString()).document("login credential").set(data)

                            Toast.makeText(
                                this@registeractivity, "Authentication successful.",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@registeractivity, loginactivity::class.java)
                            startActivity(intent)
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