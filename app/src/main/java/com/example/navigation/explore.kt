package com.example.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class explore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        val back = findViewById<ImageView>(R.id.imageview)

        back.setOnClickListener()
        {
            finish()
        }
    }
}