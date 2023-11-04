package com.example.kotlinfragment

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.kotlinfragment.fragment.DashboardFragment
import com.example.kotlinfragment.fragment.HomeFragment
import com.example.kotlinfragment.fragment.NotificationFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                buildFrag("HOMEFRAGMENT", ::HomeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                buildFrag("DASHBOARDFRAGMENT", ::DashboardFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                buildFrag("NOTIFICATIONFRAGMENT", ::NotificationFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun<T> buildFrag(tag: String, klass: () -> T) {
        val fragmentTransaction = supportFragmentManager.beginTransaction();
        var home = supportFragmentManager.findFragmentByTag(tag) as T
        if (home == null) {
            home = klass()
        }

        fragmentTransaction.replace(R.id.fragment_container, home as Fragment, tag)
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        buildFrag("HOMEFRAGMENT", ::HomeFragment)
    }
}
