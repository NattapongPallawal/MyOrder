package com.example.natta.myorder.view.orderdetail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.natta.myorder.R

class StatusAdapter : RecyclerView.Adapter<StatusAdapter.StatusViewHolder>() {
    lateinit var context: Context
    var viewType: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.list_status, parent, false)
        this.viewType = viewType
        return StatusViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {

    }


    inner class StatusViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    }
}