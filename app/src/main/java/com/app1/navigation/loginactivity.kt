package com.app1.navigation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class loginactivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginactivity)

        auth = Firebase.auth

        val email = findViewById<EditText>(R.id.editTextTextPersonName)

        val password = findViewById<EditText>(R.id.editTextTextPassword)

        val register = findViewById<Button>(R.id.button22)

        val resetPassword = findViewById<TextView>(R.id.textView6)

        val proceed = findViewById<Button>(R.id.button99)

        val imageview = findViewById<ImageView>(R.id.imageView4)

        Glide.with(this).load(R.mipmap.ic_launcher_foreground).circleCrop().into(imageview)

        if (auth.currentUser != null)
        {
            val intent = Intent(this@loginactivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        else {
            proceed.setOnClickListener()
            { view ->

                var x = 0

                if (email.text.toString().isEmpty() || password.text.toString().isEmpty())
                {
                    Snackbar.make(view, "Please enter value for all fields", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(Color.DKGRAY).setTextColor(Color.WHITE).show()
                    x++;
                }

                if (x == 0)
                { auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                    .addOnCompleteListener(this)
                    { task ->
                        if (task.isSuccessful)
                        {

                            Toast.makeText(
                                this@loginactivity, "Login Successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@loginactivity, MainActivity::class.java)
                            startActivity(intent)

                        }

                        else
                        {
                            Toast.makeText(
                                this@loginactivity, "Login failed.",
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

            resetPassword.setOnClickListener()
            {
                val intent = Intent(this@loginactivity, passwordresetactivity::class.java)
                startActivity(intent)
            }
        }
    }
}