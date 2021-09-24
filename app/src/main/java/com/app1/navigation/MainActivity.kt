package com.app1.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingFragment=setting()
        val homeFragment=user()
        val exploreFragment=explore()

        setCurrentFragment(homeFragment)

        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomnav)

        bottomnav.setOnNavigationItemSelectedListener()
        {
            when(it.itemId)
            {
                R.id.setting->setCurrentFragment(settingFragment)
                R.id.home->setCurrentFragment(homeFragment)
                R.id.explore->setCurrentFragment(exploreFragment)
            }
            true
        }

        Firebase.messaging.subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                var msg = "success"
                if (!task.isSuccessful) {
                    msg = "Failed"
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

        val drawerlayout = findViewById<DrawerLayout>(R.id.drawerlayout)

        val navigationview = findViewById<NavigationView>(R.id.navigationview)

        findViewById<ImageView>(R.id.imageview).setOnClickListener()
        {
            drawerlayout.openDrawer(GravityCompat.START)
        }

        val navcontroller = Navigation.findNavController(this, R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(navigationview, navcontroller)
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,fragment)
            commit()
        }
}