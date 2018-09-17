package com.example.natta.myorder.view

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.view.aboutapplication.AboutApplicationActivity
import com.example.natta.myorder.view.favorite.FavoriteActivity
import com.example.natta.myorder.view.login.LoginActivity
import com.example.natta.myorder.view.myprofile.MyProfileActivity
import com.example.natta.myorder.view.opsl.OPSLActivity
import com.example.natta.myorder.view.orderhistory.OrderHistoryActivity
import com.example.natta.myorder.view.randomfood.RandomFoodActivity
import com.example.natta.myorder.view.restaurant.RestaurantActivity
import com.example.natta.myorder.view.scanqrcode.ScanQRCodeActivity
import com.example.natta.myorder.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter
import uk.co.markormesher.android_fab.SpeedDialMenuItem

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var editor: SharedPreferences.Editor? = null
    private val FROM_RESTAURANT = "FROM_RESTAURANT"

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
                    editor!!.putInt(FROM_RESTAURANT, 0)
                    editor!!.commit()
                    startActivity(Intent(applicationContext, RestaurantActivity::class.java))
                }
                1 -> {
                    editor!!.putInt(FROM_RESTAURANT, 1)
                    editor!!.commit()
                    startActivity(Intent(applicationContext, ScanQRCodeActivity::class.java))

                }
            }
            return true
        }

        override fun fabRotationDegrees(): Float = 45f

    }
    private lateinit var model: MainViewModel
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        model = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        editor = this.getSharedPreferences("MY_ORDER", Context.MODE_PRIVATE).edit()
        initMainTabPager()
        initFabAddOrder()
        nav_view.setNavigationItemSelectedListener(this)

        Delay(1000).execute()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(customer: Customer, email: String) {
        try {
            name_profile_main.text = customer.firstName.toString() + " " + customer.lastName.toString()
            email_profile_main.text = email

        } catch (e: IllegalStateException) {

        }
        try {
            Picasso.get().load(customer.picture).into(pic_profile_main)
        } catch (e: IllegalArgumentException) {
        }
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
                startActivity(Intent(applicationContext, MyProfileActivity::class.java))
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
            R.id.nav_about -> {
                startActivity(Intent(applicationContext, AboutApplicationActivity::class.java))
            }
            R.id.nav_osl -> {
                startActivity(Intent(applicationContext, OPSLActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getCustomer() {
        model.getCustomer().observe(this, Observer {
            Log.d("Cus444", "${it?.firstName}")
//            name_profile_main.text = it?.firstName.toString()
            if (it != null) {
                updateUI(it, model.getEmail())
            }
        })

    }

    @SuppressLint("StaticFieldLeak")
    internal inner class Delay(var time: Long) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            Thread.sleep(time)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            getCustomer()
        }

    }
}
