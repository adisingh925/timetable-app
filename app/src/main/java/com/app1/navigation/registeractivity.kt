package com.app1.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
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

        val email = findViewById<EditText>(R.id.editTextTextPersonName)

        val password = findViewById<EditText>(R.id.editTextTextPassword)

        val name = findViewById<EditText>(R.id.editTextTextPersonName3)

        val register = findViewById<Button>(R.id.button7)

        val icon = findViewById<ImageView>(R.id.imageView4)

        Glide.with(this).load(R.mipmap.ic_launcher_foreground).circleCrop().into(icon)

        register.setOnClickListener()
        {
            var x =0
            if(email.text.toString().isEmpty() || password.text.toString().isEmpty() || name.text.toString().isEmpty())
            {
                Toast.makeText(
                    this@registeractivity, "Please Enter Value for all Fields",
                    Toast.LENGTH_SHORT
                ).show()
                x++
            }

            if(x==0)
            {
                auth.createUserWithEmailAndPassword(
                    email.text.toString(),
                    password.text.toString()
                )
                    .addOnCompleteListener(this)
                    { task ->
                        if (task.isSuccessful)
                        {

                            val data = hashMapOf("name" to name.text.toString(),"email" to email.text.toString(),"status" to "false", "imagepath" to null)
                            db.collection("userdata").document("userdata").collection(auth.uid!!).document("login credentials").set(data)

                            db.collection("userdata").document("explore").get().addOnSuccessListener()
                            {
                                document->
                                if(document.exists())
                                {
                                    var num = document.getString("count")?.toInt()
                                    if (num != null) {
                                        num += 1
                                    }
                                    val data1 = hashMapOf(auth.uid.toString() to "$num","username$num" to name.text.toString(), "userimage$num" to null, "count" to num.toString(),"status$num" to "false")
                                    db.collection("userdata").document("explore").set(data1,
                                        SetOptions.merge())
                                }
                                else
                                {
                                    val data1 = hashMapOf(auth.uid.toString() to "1","username1" to name.text.toString(), "userimage1" to null, "count" to "1","status1" to "false")
                                    db.collection("userdata").document("explore").set(data1)
                                }
                            }

                            Toast.makeText(
                                this@registeractivity, "Registration successful.",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@registeractivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        }

                        else
                        {
                            Toast.makeText(
                                this@registeractivity, "Registration failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}