package com.example.natta.myorder.view.orderdetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.natta.myorder.R
import com.example.natta.myorder.data.OrderMenu
import kotlinx.android.synthetic.main.list_payment.view.*

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.FoodOrderDetailViewHolder>() {
    private var menu = arrayListOf<Pair<String, OrderMenu>>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodOrderDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_payment, parent, false)
        return FoodOrderDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menu.size
    }

    override fun onBindViewHolder(holder: FoodOrderDetailViewHolder, position: Int) {

    }

    fun setData(menu: ArrayList<Pair<String, OrderMenu>>) {
        this.menu = menu
        notifyDataSetChanged()
    }

    inner class FoodOrderDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var amount = view.amount_PML as TextView
        var foodName = view.foodName_PML as TextView
        var price = view.price_PML as TextView
    }
}