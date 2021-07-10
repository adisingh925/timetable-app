package com.app1.navigation

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

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