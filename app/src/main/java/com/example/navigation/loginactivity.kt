package com.example.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class loginactivity : AppCompatActivity()
{

    lateinit var email:String

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginactivity)

        var flag = 0

        auth = Firebase.auth

        val db = Firebase.firestore

        val name = findViewById<EditText>(R.id.editTextTextPersonName)

        val pass = findViewById<EditText>(R.id.editTextTextPassword)

        val register = findViewById<Button>(R.id.button22)

        val resetp = findViewById<TextView>(R.id.textView6)

        val proceed = findViewById<Button>(R.id.button99)

        if(auth.currentUser!=null)
        {
            val intentt = Intent(this@loginactivity,MainActivity::class.java)
            startActivity(intentt)
            finish()
        }


           // val emailpattern = "[a-zA-Z0-9._]+@[a-zA-Z]+.com".toRegex()

            proceed.setOnClickListener()
            {
                var x = 0

                if (name.text.toString().isEmpty() || pass.text.toString().isEmpty()) {
                    Toast.makeText(
                        this@loginactivity, "Please Enter value for both input fields",
                        Toast.LENGTH_SHORT
                    ).show()

                    x++;
                }

                val docref =
                    db.collection("user data").document("user data").collection(name.text.toString())
                        .document("login credentials")

                docref.get().addOnSuccessListener()
                {
                    document->
                    if((name.text.toString() == document.getString("username").toString())  && (pass.text.toString() == document.getString("password").toString()))
                    {
                        email = document.getString("email").toString()
                    }
                }

                if (x == 0) {
                    auth.signInWithEmailAndPassword(email, pass.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {

                                Toast.makeText(
                                    this@loginactivity, "Authentication Successful",
                                    Toast.LENGTH_SHORT
                                ).show()


                                val intent =
                                    Intent(this@loginactivity, MainActivity::class.java)
                                intent.putExtra("name", name.text.toString())
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@loginactivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }


            }


            register.setOnClickListener()
            {
                val intent = Intent(this@loginactivity, registeractivity::class.java)
                startActivity(intent)
            }

            resetp.setOnClickListener()
            {
                val intent = Intent(this@loginactivity, passwordresetactivity::class.java)
                startActivity(intent)
            }
        }

    }