package com.example.natta.myorder.view

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.RandomFoodActivity
import com.example.natta.myorder.view.favorite.FavoriteActivity
import com.example.natta.myorder.view.food.FoodActivity
import com.example.natta.myorder.view.login.LoginActivity
import com.example.natta.myorder.view.myprofile.MyProfileActivity
import com.example.natta.myorder.view.orderhistory.OrderHistoryActivity
import com.example.natta.myorder.view.restaurant.RestaurantActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter
import uk.co.markormesher.android_fab.SpeedDialMenuItem

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val tab = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            val color = ContextCompat.getColor(applicationContext, R.color.colorTab)
            tab!!.icon!!.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            val color = ContextCompat.getColor(applicationContext, R.color.colorTabSelected)
            tab!!.icon!!.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }

    }
    private val item = arrayOf("ทานที่บ้าน", "ทานที่ร้าน")
    private val speedDialMenuAdapter = object : SpeedDialMenuAdapter() {
        override fun getCount(): Int {
            return 2
        }

        override fun getMenuItem(context: Context, position: Int): SpeedDialMenuItem = when (position) {
            0 -> SpeedDialMenuItem(context, R.drawable.ic_back_home, item[position])
            1 -> SpeedDialMenuItem(context, R.drawable.ic_store, item[position])
            else -> throw IllegalArgumentException("No menu item: $position")
        }

        override fun onMenuItemClick(position: Int): Boolean {
            when (position) {
                0 -> {
                    Toast.makeText(applicationContext, item[position], Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext, RestaurantActivity::class.java))
                }
                1 -> {
                    Toast.makeText(applicationContext, item[position], Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext, FoodActivity::class.java))

                }
            }
            return true
        }

        override fun fabRotationDegrees(): Float = 45f

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        initMainTabPager()
        initFabAddOrder()
        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun initFabAddOrder() {
        fab.speedDialMenuAdapter = speedDialMenuAdapter
        fab.contentCoverEnabled = false
    }

    private fun initMainTabPager() {
        val tabPager = MainTabPager(supportFragmentManager)
        viewPager.adapter = tabPager
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.icon = resources.getDrawable(R.drawable.ic_home)
        tabLayout.getTabAt(1)!!.icon = resources.getDrawable(R.drawable.ic_order)
        tabLayout.getTabAt(2)!!.icon = resources.getDrawable(R.drawable.ic_notifications)
        tabLayout.getTabAt(0)!!.select()
        if (tabLayout.getTabAt(0)!!.isSelected) {
            val color = ContextCompat.getColor(applicationContext, R.color.colorTabSelected)
            tabLayout.getTabAt(0)!!.icon!!.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }

        tabLayout.setOnTabSelectedListener(tab)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_profile -> {
                startActivity(Intent(applicationContext,MyProfileActivity::class.java))
            }
            R.id.nav_order_history -> {
                startActivity(Intent(applicationContext, OrderHistoryActivity::class.java))
            }
            R.id.nav_logout -> {
                val mAuth = FirebaseAuth.getInstance()
                if (mAuth.currentUser != null) {
                    mAuth.signOut()
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                }
            }
            R.id.nav_favortie -> {
                startActivity(Intent(applicationContext, FavoriteActivity::class.java))
            }
            R.id.nav_random -> {
                startActivity(Intent(applicationContext, RandomFoodActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
