package com.example.natta.myorder.view.restaurant

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Restaurant
import com.example.natta.myorder.view.restaurantdetail.RestaurantDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_restaurant.view.*

class RestaurantAdapter(var context: Context, private var latitude: Double, private var longitude: Double) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    private var restaurantList = listOf<Restaurant>()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_restaurant, p0, false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: RestaurantViewHolder, p1: Int) {

        val distance = calculateDistance(
                restaurantList[p1].address!!.latitude,
                restaurantList[p1].address!!.longitude)

        Picasso.get().load(restaurantList[p1].picture).into(p0.imageView)
        p0.resName.text = restaurantList[p1].restaurantName.toString()
        p0.rating.rating = restaurantList[p1].rating!!.toFloat()
        p0.resTime.text = "เปิด ${restaurantList[p1].timeOpen} - ${restaurantList[p1].timeClose} น."
        p0.resLocation.text = "$distance กม."

        p0.btnOrderFood.setOnClickListener {
            Toast.makeText(context,"Order!!!!!!!!!",Toast.LENGTH_LONG).show()
        }
        p0.restaurantView.setOnClickListener {
            val i = Intent(context, RestaurantDetailActivity::class.java)
            i.putExtra("restaurant",restaurantList[p1])
            i.putExtra("distance",distance)
            context.startActivity(i)
        }
    }

    private fun calculateDistance(latitude: Double?, longitude: Double?): String {
        val resLocation = Location("resLocation")
        resLocation.longitude = longitude!!
        resLocation.latitude = latitude!!
        val currentLocation = Location("currentLocation")
        currentLocation.longitude = this.longitude
        currentLocation.latitude = this.latitude
        val distance = currentLocation.distanceTo(resLocation) / 1000
        return String.format("%.1f", distance)
    }

    fun setData(restaurantList: List<Restaurant>) {
        this.restaurantList = restaurantList
        notifyDataSetChanged()

    }

    inner class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView = view.imageView_res as ImageView
        var resName = view.resName_res as TextView
        var resTime = view.resTime_res as TextView
        var resLocation = view.resLocation_res as TextView
        var rating = view.rating_res as RatingBar
        var btnOrderFood = view.btnOrderFood_res as TextView
        var restaurantView = view.restaurantView as ConstraintLayout


    }
}