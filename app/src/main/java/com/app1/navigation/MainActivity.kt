package com.app1.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import java.util.*

class MainActivity : AppCompatActivity() {

    var auth = Firebase.auth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Firebase.messaging.subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                var msg = "success"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
            }

        val calendar = Calendar.getInstance()

        val day = calendar.get(Calendar.DAY_OF_WEEK)

        when(day)
        {
            Calendar.SUNDAY -> {
                val sunday1 = Intent(this, sunday::class.java)
                startActivity(sunday1)
            }
            Calendar.MONDAY -> {
                val monday1 = Intent(this, monday::class.java)
                startActivity(monday1)
            }
            Calendar.TUESDAY -> {
                val tuesday1 = Intent(this, tuesday::class.java)
                startActivity(tuesday1)
            }
            Calendar.WEDNESDAY -> {
                val wednesday1 = Intent(this, wednesday::class.java)
                startActivity(wednesday1)
            }
            Calendar.THURSDAY -> {
                val thursday1 = Intent(this, thursday::class.java)
                startActivity(thursday1)
            }
            Calendar.FRIDAY -> {
                val friday1 = Intent(this, friday::class.java)
                startActivity(friday1)
            }
            Calendar.SATURDAY -> {
                val saturday1 = Intent(this, saturday::class.java)
                startActivity(saturday1)
            }

        }

        val immg = findViewById<ImageView>(R.id.immg)

        val logout = findViewById<ImageView>(R.id.imageview1)

        logout.setOnClickListener()
        {
            auth.signOut()
            val inte = Intent(this@MainActivity,loginactivity::class.java)
            startActivity(inte)
        }

       /* Glide.with(this)
            .load("https://etimg.etb2bimg.com/photo/73164879.cms")
            .circleCrop()
            .into(immg)*/

        val drawerlayout = findViewById<DrawerLayout>(R.id.drawerlayout)

        val navigationview = findViewById<NavigationView>(R.id.navigationview)

        findViewById<ImageView>(R.id.imageview).setOnClickListener()
        {
            drawerlayout.openDrawer(GravityCompat.START)
        }

        val navcontroller = Navigation.findNavController(this, R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(navigationview, navcontroller)

    }
}