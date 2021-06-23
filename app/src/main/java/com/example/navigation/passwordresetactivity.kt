package com.example.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class passwordresetactivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passwordresetactivity)

        auth = Firebase.auth

        val emm = findViewById<EditText>(R.id.editTextTextEmailAddress)

        val butto = findViewById<Button>(R.id.button8)

        //val confi = "[0-9a-zA-Z]+@[a-zA-Z]+.com".toRegex()

        butto.setOnClickListener()
        {
            var x =0
            if(emm.text.toString().isEmpty())
            {
                Toast.makeText(
                    this@passwordresetactivity,
                    "Please Enter an Email",
                    Toast.LENGTH_SHORT
                ).show()
                x++
            }

           /* if(!emm.text.toString().matches(confi) && x==0)
            {
                Toast.makeText(
                    this@passwordresetactivity,
                    "Please Enter a Valid Email",
                    Toast.LENGTH_SHORT
                ).show()
                x++
            }*/
            if(x==0) {
                auth.sendPasswordResetEmail(emm.text.toString()).addOnCompleteListener(this)
                { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@passwordresetactivity,
                            "Email Sent Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@passwordresetactivity, loginactivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@passwordresetactivity,
                            "You are not registered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}