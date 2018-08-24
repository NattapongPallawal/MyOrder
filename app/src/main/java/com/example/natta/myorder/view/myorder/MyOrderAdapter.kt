package com.example.natta.myorder.view.myorder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.natta.myorder.R

class MyOrderAdapter(var context:Context) : RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_my_order,parent,false)
        return MyOrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {
    }


    inner class MyOrderViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }
}