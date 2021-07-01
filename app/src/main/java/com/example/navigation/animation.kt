package com.example.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class animation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        Handler().postDelayed({
            val intent = Intent(this, loginactivity::class.java)
            startActivity(intent)
            finish()
        }, 300)
    }
}