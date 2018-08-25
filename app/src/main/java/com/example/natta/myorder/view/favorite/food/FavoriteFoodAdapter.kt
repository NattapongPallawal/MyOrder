package com.example.natta.myorder.view.favorite.food

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.natta.myorder.R

class FavoriteFoodAdapter(var context:Context) : RecyclerView.Adapter<FavoriteFoodAdapter.FavoriteFoodViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_food_fav,parent,false)
        return FavoriteFoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: FavoriteFoodViewHolder, position: Int) {
    }

    inner class FavoriteFoodViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }
}