package com.example.natta.myorder.view.restaurant

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.viewmodel.RestaurantViewModel
import kotlinx.android.synthetic.main.activity_restaurant.*


class RestaurantActivity : AppCompatActivity() {
    private var latitude = 0.0
    private var longitude = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val model = ViewModelProviders.of(this).get(RestaurantViewModel::class.java)
        getLocation()
        val adapter = RestaurantAdapter(applicationContext, latitude, longitude)
        recyclerView_restaurant.adapter = adapter
        recyclerView_restaurant.layoutManager = LinearLayoutManager(applicationContext)

        model.getRestaurant().observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        val location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)

        when {
            location != null -> {
                latitude = location.latitude
                longitude = location.longitude
                Toast.makeText(this, "$longitude , $latitude", Toast.LENGTH_SHORT).show()


            }
            location1 != null -> {
                latitude = location1.latitude
                longitude = location1.longitude
                Toast.makeText(this, "$longitude , $latitude", Toast.LENGTH_SHORT).show()


            }
            location2 != null -> {
                latitude = location2.latitude
                longitude = location2.longitude
                Toast.makeText(this, "$longitude , $latitude", Toast.LENGTH_SHORT).show()

            }
            else -> Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show()
        }

    }


}
