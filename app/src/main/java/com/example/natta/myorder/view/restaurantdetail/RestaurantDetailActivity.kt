package com.example.natta.myorder.view.restaurantdetail

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.data.Restaurant
import com.example.natta.myorder.viewmodel.RestaurantDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_restaurant_detail.*
import kotlinx.android.synthetic.main.content_restaurant_detail.*

class RestaurantDetailActivity : AppCompatActivity() {
    var restaurant = Restaurant()
    var distance = "0"
    var model = RestaurantDetailViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        model = ViewModelProviders.of(this).get(RestaurantDetailViewModel::class.java)

        restaurant = intent.getParcelableExtra("restaurant")
        distance = intent.getStringExtra("distance")

        val adapter = FeedbackAdapter(applicationContext)
        loadImage()
        setTextValue()
        recycleView_feedback.adapter = adapter
        recycleView_feedback.layoutManager = LinearLayoutManager(applicationContext)

        model.getFeedback("res-test1").observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
            }
        })

    }

    private fun loadImage() {
        Picasso.get().load(restaurant.picture).into(resImage_RD)
    }

    @SuppressLint("SetTextI18n")
    private fun setTextValue() {
        resName_RD.text = restaurant.restaurantName.toString()
        resLocation_RD.text = try {
            restaurant.address!!.address.toString()
        } catch (e: KotlinNullPointerException) {
            "ไม่พบ"
        }
        resOpen_RD.text = "เปิด ${restaurant.timeOpen} - ${restaurant.timeClose} น."
        resDistance_RD.text = "$distance  กม."
        resRating_RD.rating = restaurant.rating!!.toFloat()
        resCall_RD.text = restaurant.phoneNumber.toString()
        resAbout_RD.text = restaurant.about.toString()
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
