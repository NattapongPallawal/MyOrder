package com.example.natta.myorder.view.notification

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Notification
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.list_notification.view.*
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    private var noti = listOf<Notification>()
    private var mRootRef = FirebaseDatabase.getInstance().reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noti.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {

        holder.message.text = noti[position].message.toString()
        holder.time.text = convertDate(noti[position].date as Long ,false)
        holder.date.text = convertDate(noti[position].date as Long ,true)
        holder.res.text = noti[position].restaurantName.toString()
        holder.orderNum.text = noti[position].orderNumber.toString()

    }

    @SuppressLint("SimpleDateFormat")
    private fun convertDate(t: Long, date: Boolean): String {
//        SimpleDateFormat("ddMMyyyy HH:mm:ss").format(Date(1536671117304)).toString()
        val d = Date(t)
        val orderDate = SimpleDateFormat("ddMMyyyy").format(d).toLong()
        val today = SimpleDateFormat("ddMMyyyy").format(Date()).toLong()
        val fmD = SimpleDateFormat("dd")
        val fmM = SimpleDateFormat("MM")
        val fmY = SimpleDateFormat("yyyy")
        val fmT = SimpleDateFormat("HH:mm")

        val day = fmD.format(d)
        val month = fmM.format(d)
        val year = (fmY.format(d).toInt() + 543).toString()
        val time = fmT.format(d)

        return if (date) {
            if (orderDate == today) {
                "วันนี้"
            } else {
                "$day/$month/$year"
            }
        } else {
            "$time น."

        }
    }

    fun setData(noti: List<Notification>) {
        this.noti = noti
        notifyDataSetChanged()
    }

    inner class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var orderNum = view.order_num_noti as TextView
        var res = view.res_noti as TextView
        var message = view.message_noti as TextView
        var time = view.time_noti as TextView
        var date = view.date_noti as TextView
    }
}