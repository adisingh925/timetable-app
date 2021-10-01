package com.app1.navigation

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import java.util.*

class MainActivity : AppCompatActivity() {

    var auth = Firebase.auth

    lateinit var drawerlayout:DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //firebase inapp messaging
        Firebase.messaging.subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                var msg = "success"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
            }

        val logout = findViewById<ImageView>(R.id.imageview1)

        logout.setOnClickListener()
        {
            auth.signOut()
            val intent = Intent(this@MainActivity, loginactivity::class.java)
            startActivity(intent)
        }

        drawerlayout = findViewById<DrawerLayout>(R.id.drawerlayout)

        val navigationview = findViewById<NavigationView>(R.id.navigationview)

        findViewById<ImageView>(R.id.imageview).setOnClickListener()
        {
            drawerlayout.openDrawer(GravityCompat.START)
        }

        val navcontroller = Navigation.findNavController(this, R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(navigationview, navcontroller)
    }

    override fun onBackPressed() {
        if(drawerlayout.isDrawerOpen(GravityCompat.START))
        {
            drawerlayout.closeDrawer(GravityCompat.START)
        }
        else
        {
            super.onBackPressed()
        }
    }
}