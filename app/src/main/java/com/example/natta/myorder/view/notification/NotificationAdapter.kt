package com.example.natta.myorder.view.notification

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Notification
import com.example.natta.myorder.data.Order
import com.example.natta.myorder.data.Restaurant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.list_notification.view.*

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
//       val orderListener = object : ValueEventListener{
//           override fun onCancelled(p0: DatabaseError) {
//
//           }
//
//           override fun onDataChange(p0: DataSnapshot) {
//               holder.orderNum.text = p0.getValue(Order::class.java)!!.orderNumber.toString()
//           }
//
//       }
//        val resListener = object : ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                holder.res.text = p0.getValue(Restaurant::class.java)!!.restaurantName.toString()
//            }
//
//        }
//        val mResRef = mRootRef.child("restaurant").child(noti[position].restaurant)
//        val orderRef = mRootRef.child("order").child("cus-test1").child(noti[position].order)
//
//        mResRef.addValueEventListener(resListener)
//        orderRef.addValueEventListener(orderListener)
//        holder.message.text = noti[position].message
//        holder.time.text = noti[position].date
//        holder.date.text = noti[position].date

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