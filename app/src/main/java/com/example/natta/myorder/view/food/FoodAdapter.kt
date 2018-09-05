package com.example.natta.myorder.view.food

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.view.fooddetail.FoodDetailActivity
import kotlinx.android.synthetic.main.list_food.view.*

class FoodAdapter(var context: Context) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    private var food= arrayListOf<Pair<String, Food>>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return food.size

    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.name.text = food[position].second.foodName
        holder.rating.rating = food[position].second.rate!!.toFloat()
        holder.price.text = food[position].second.price.toString()
        Glide.with(context).load(food[position].second.picture).into(holder.picture)



        holder.cardFood.setOnClickListener {
            val i = Intent(context.applicationContext, FoodDetailActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.putExtra("food",food[position].second)
            i.putExtra("foodKey",food[position].first)
            context.startActivity(i)
        }
    }

    fun setData(food: ArrayList<Pair<String, Food>>?) {
        this.food = food!!
        notifyDataSetChanged()
    }

    inner class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardFood = view.cardFood as CardView
        var name = view.foodName_food as TextView
        var rating = view.rating_food as RatingBar
        var price = view.price_food as TextView
        var picture = view.picture_food as ImageView
    }
}