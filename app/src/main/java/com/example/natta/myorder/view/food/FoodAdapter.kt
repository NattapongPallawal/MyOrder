package com.example.natta.myorder.view.food

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.view.fooddetail.FoodDetailActivity
import kotlinx.android.synthetic.main.list_food.view.*

class FoodAdapter(var context: Context) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    private var food = listOf<Food>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return food.size

    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.cardFood.setOnClickListener {
            val i = Intent(context.applicationContext, FoodDetailActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    fun setData(food: List<Food>) {
        this.food = food
        notifyDataSetChanged()
    }

    inner class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardFood = view.cardFood as CardView
    }
}