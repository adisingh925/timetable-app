package com.app1.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
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

        val textview = findViewById<TextView>(R.id.textview1)

        val Navigationview = findViewById<NavigationView>(R.id.navigationview)

        Navigationview.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.sunday->{
                    setCurrentFragment(sunday())
                    textview.text="Sunday"
                }
                R.id.monday->{
                    setCurrentFragment(monday())
                    textview.text="Monday"
                }
                R.id.tuesday->{
                    setCurrentFragment(tuesday())
                    textview.text="Tuesday"
                }
                R.id.wednesday->{
                    setCurrentFragment(wednesday())
                    textview.text="Wednesday"
                }
                R.id.thursday->{
                    setCurrentFragment(thursday())
                    textview.text="Thursday"
                }
                R.id.friday->{
                    setCurrentFragment(friday())
                    textview.text="Friday"
                }
                R.id.saturday->{
                    setCurrentFragment(saturday())
                    textview.text="Saturday"
                }
                R.id.explore->{
                    setCurrentFragment(explore())
                    textview.text="Explore"
                }
                R.id.user->{
                    setCurrentFragment(user())
                    textview.text="Home"
                }
                R.id.settings->{
                    setCurrentFragment(setting())
                    textview.text="Setting"
                }
                else -> textview.text=""
            }
            drawerlayout.closeDrawer(GravityCompat.START)
            true
        }

        findViewById<ImageView>(R.id.imageview).setOnClickListener()
        {
            drawerlayout.openDrawer(GravityCompat.START)
        }

        /*val navcontroller = Navigation.findNavController(this, R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(Navigationview, navcontroller)*/
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

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,fragment)
            commit()
        }
}