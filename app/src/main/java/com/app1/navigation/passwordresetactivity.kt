package com.app1.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class passwordresetactivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passwordresetactivity)

        auth = Firebase.auth

        val email = findViewById<EditText>(R.id.editTextTextEmailAddress)

        val button = findViewById<Button>(R.id.button8)

        button.setOnClickListener()
        {
            var x =0
            if(email.text.toString().isEmpty())
            {
                Toast.makeText(
                    this@passwordresetactivity,
                    "Please Enter an Email",
                    Toast.LENGTH_SHORT
                ).show()
                x++
            }

            if(x==0) {
                auth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener(this)
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