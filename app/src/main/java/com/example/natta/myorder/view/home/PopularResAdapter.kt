package com.example.natta.myorder.view.home

import android.content.Context
import android.location.Location
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Restaurant
import kotlinx.android.synthetic.main.list_restaurant_home.view.*

class PopularResAdapter(private var latitude: Double, private var longitude: Double) : RecyclerView.Adapter<PopularResAdapter.PopularResViewHolder>() {
    private var popularRes = arrayListOf<Pair<String, Restaurant>>()
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularResViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_restaurant_home, parent, false)
        return PopularResViewHolder(v)
    }

    override fun getItemCount(): Int {
        return popularRes.size
    }

    override fun onBindViewHolder(holder: PopularResViewHolder, position: Int) {
        holder.resName.text = popularRes[position].second.restaurantName
        holder.rating.rating = popularRes[position].second.rating!!.toFloat()
        holder.distance.text = calculateDistance(popularRes[position].second.address!!.latitude, popularRes[position].second.address!!.longitude)
        Glide.with(context)
                .load(popularRes[position].second.picture)
//                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(10,0,RoundedCornersTransformation.CornerType.ALL)))
                .into(holder.img)
    }

    fun setData(popularRes: ArrayList<Pair<String, Restaurant>>) {
        popularRes.sortWith(Comparator { o1, o2 ->
            val a = calculateDistance(o1!!.second.address!!.latitude, o1.second.address!!.longitude, true).toFloat()
            val b = calculateDistance(o2!!.second.address!!.latitude, o2.second.address!!.longitude, true).toFloat()
            when {
                a > b -> 1
                a == b -> 0
                else -> -1
            }
        })
        val temp = arrayListOf<Pair<String, Restaurant>>()
        for (i in popularRes) {
            if (temp.size < 5 && temp.size != popularRes.size) {
                if (calculateDistance(i.second.address!!.latitude, i.second.address!!.longitude, true).toFloat() <= 10f)
                    temp.add(i)
            } else {
                break
            }
        }
        temp.sortWith(Comparator { o1, o2 ->
            val a = o1.second.rating!!
            val b = o2.second.rating!!
            when {
                a < b -> 1
                a == b -> 0
                else -> -1
            }
        })
        this.popularRes = temp
        notifyDataSetChanged()
    }

    private fun calculateDistance(latitude: Double?, longitude: Double?, fromSetData: Boolean = false): String {
        val resLocation = Location("resLocation")
        resLocation.longitude = longitude!!
        resLocation.latitude = latitude!!
        val currentLocation = Location("currentLocation")
        currentLocation.longitude = this.longitude
        currentLocation.latitude = this.latitude
        val distance = currentLocation.distanceTo(resLocation) / 1000
        return if (fromSetData) distance.toString() else String.format("%.1f", distance)
    }

    inner class PopularResViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var resName = view.res_name_homeF as TextView
        var rating = view.ratingBar_home as RatingBar
        var distance = view.distance_home as TextView
        var img = view.img_res_home as ImageView

    }
}


