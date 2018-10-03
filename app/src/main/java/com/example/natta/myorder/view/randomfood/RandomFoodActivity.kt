package com.example.natta.myorder.view.randomfood

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.viewmodel.RandomFoodViewModel
import kotlinx.android.synthetic.main.activity_random_food.*
import java.util.*


class RandomFoodActivity : AppCompatActivity() {
    private lateinit var model: RandomFoodViewModel
    private var latitude = 0.0
    private var longitude = 0.0
    private var foundCurrentLocation = MutableLiveData<Boolean>()
    private var menu = arrayListOf<Pair<String, Food>>()

    init {
        foundCurrentLocation.value = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_food)
        model = ViewModelProviders.of(this).get(RandomFoodViewModel::class.java)
        getLocation()
        foundCurrentLocation.observe(this, Observer {
            if (it != null) {
                if (it) {
                    model.setLocation(latitude, longitude)
                }
            }
        })

        model.getMenu().observe(this, Observer {
            menu = it!!
            Log.d("randomFMain", "$it")

        })

        btn_random.setOnClickListener {
            textView_res_random.text = model.getRestaurantName().restaurantName
            RandomFoodDelay().execute()
        }


    }

    private fun <E> List<E>.getRandomElement(): E {
        return this[Random().nextInt(this.size)]
    }

    @SuppressLint("StaticFieldLeak")
    internal inner class RandomFoodDelay : AsyncTask<Void, Void, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
            textView_random.setTextColor(Color.WHITE)
        }
        override fun doInBackground(vararg params: Void?): Void? {
            for (i in 1..100) {
                runOnUiThread {

                    textView_random.text = menu.getRandomElement().second.foodName.toString()
                }
                Thread.sleep((i).toLong())
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            textView_random.setTextColor(Color.YELLOW)
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        val location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)

        when {
            location != null -> {
                latitude = location.latitude
                longitude = location.longitude
//                Toast.makeText(this, "$longitude , $latitude", Toast.LENGTH_SHORT).show()
                foundCurrentLocation.value = true
            }
            location1 != null -> {
                latitude = location1.latitude
                longitude = location1.longitude
//                Toast.makeText(this, "$longitude , $latitude", Toast.LENGTH_SHORT).show()
                foundCurrentLocation.value = true

            }
            location2 != null -> {
                latitude = location2.latitude
                longitude = location2.longitude
//                Toast.makeText(this, "$longitude , $latitude", Toast.LENGTH_SHORT).show()
                foundCurrentLocation.value = true

            }
            else -> {
                Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show()
                foundCurrentLocation.value = false
            }
        }

    }
}
