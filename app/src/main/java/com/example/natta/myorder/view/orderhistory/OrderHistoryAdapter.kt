package com.example.natta.myorder.view.orderhistory

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Order
import kotlinx.android.synthetic.main.list_order_history.view.*

class OrderHistoryAdapter(var context: Context) : RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryHolder>() {
    private var orderList= listOf<Order>()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OrderHistoryHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_order_history, p0, false)
        return OrderHistoryHolder(view)

    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(p0: OrderHistoryHolder, p1: Int) {
        p0.resName.text = orderList[p1].restaurantID.toString()
        p0.amountMenu.text = "5"
        p0.date.text = orderList[p1].date.toString()
        p0.price.text = orderList[p1].total.toString()
        p0.orderID.text = orderList[p1].orderNumber.toString()

    }

    fun setData(orders: List<Order>) {
        orderList = orders
        notifyDataSetChanged()
    }

    inner class OrderHistoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        var resName = view.resName_oh as TextView
        var amountMenu = view.amountMenu_oh as TextView
        var date = view.date_oh as TextView
        var price = view.price_oh as TextView
        var orderID = view.orderID as TextView
    }
}