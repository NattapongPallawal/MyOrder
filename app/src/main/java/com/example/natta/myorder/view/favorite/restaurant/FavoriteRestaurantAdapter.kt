package com.example.natta.myorder.view.favorite.restaurant

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.natta.myorder.R
import com.example.natta.myorder.data.FavRes
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import kotlinx.android.synthetic.main.list_restaurant_fav.view.*

class FavoriteRestaurantAdapter:RecyclerView.Adapter<FavoriteRestaurantAdapter.FavoriteRestaurantViewHolder>() {
    private var favRes = arrayListOf<Pair<String,FavRes>>()
    private lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRestaurantViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.list_restaurant_fav, parent,false)
        return FavoriteRestaurantViewHolder(view)

    }

    override fun getItemCount(): Int {

        return favRes.size
    }

    override fun onBindViewHolder(holder: FavoriteRestaurantViewHolder, position: Int) {
        holder.resName.text = favRes[position].second.resName.toString()

        Glide.with(context)
                .load(favRes[position].second.picRes)
                .apply(RequestOptions.bitmapTransform(ColorFilterTransformation(Color.argb(70,0,0,0))))
                .into(holder.picture)

        holder.rating.rating = favRes[position].second.rating!!.toFloat()

    }

    fun setData(favRes: ArrayList<Pair<String, FavRes>>) {
        this.favRes = favRes
        notifyDataSetChanged()
    }

    inner class FavoriteRestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var resName = view.resName_FR as TextView
        var picture = view.resPic_FR as ImageView
        var rating = view.ratingRes_FR as RatingBar

    }
}