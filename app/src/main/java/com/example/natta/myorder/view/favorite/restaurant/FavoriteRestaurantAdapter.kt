package com.example.natta.myorder.view.favorite.restaurant

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.natta.myorder.R

class FavoriteRestaurantAdapter(var context : Context):RecyclerView.Adapter<FavoriteRestaurantAdapter.FavoriteRestaurantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRestaurantViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_restaurant_fav, parent,false)
        return FavoriteRestaurantViewHolder(view)

    }

    override fun getItemCount(): Int {

        return 100
    }

    override fun onBindViewHolder(holder: FavoriteRestaurantViewHolder, position: Int) {

    }

    inner class FavoriteRestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }
}