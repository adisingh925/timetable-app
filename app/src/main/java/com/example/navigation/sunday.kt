package com.example.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class sunday : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sunday)

        val back = findViewById<ImageView>(R.id.imageview)

        back.setOnClickListener()
        {
            finish()
        }
    }
}