package com.example.natta.myorder.view.order

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.natta.myorder.R

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_order,parent,false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20

    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
    }

    inner class OrderViewHolder(view : View): RecyclerView.ViewHolder(view) {

    }
}