package com.example.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

var globalname:String = "hi"

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

        val imageview4 = findViewById<ImageView>(R.id.imageView4)

       /* if(auth.currentUser!=null)
        {
            val intentt = Intent(this@loginactivity,MainActivity::class.java)
            startActivity(intentt)
            finish()
        }*/

        Glide.with(this).load(R.mipmap.ic_launcher_foreground).circleCrop().into(imageview4)

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


                else {
                    val docref =
                        db.collection("user data").document("user data")
                            .collection(name.text.toString())
                            .document("login credentials")

                    docref.get().addOnSuccessListener()
                    { document ->
                        var names = document.getString("username").toString()
                        var passs = document.getString("password").toString()
                        if ((name.text.toString() == names) && (pass.text.toString() == passs)) {
                            if (x == 0) {
                                auth.signInWithEmailAndPassword(
                                    document.getString("email").toString(), pass.text.toString()
                                )
                                    .addOnCompleteListener(this) { task ->
                                        if (task.isSuccessful) {

                                            Toast.makeText(
                                                this@loginactivity, "Authentication Successful",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            globalname = document.getString("username").toString()

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
                        else
                        {
                            Toast.makeText(
                                this@loginactivity, "Username or password is incorrect",
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