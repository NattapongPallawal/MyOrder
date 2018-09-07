package com.example.natta.myorder.view.myorder

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Select
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_my_order.view.*

class MyOrderAdapter(var context: Context) : RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder>() {
    private var myOrder = arrayListOf<Pair<String, Select>>()
    private var mRootRef = FirebaseDatabase.getInstance().reference
    private var mAuth = FirebaseAuth.getInstance()
    private var total = MutableLiveData<Double>()
    private var t: Double = 0.0

    init {
        total.value = 0.0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_my_order, parent, false)

        return MyOrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myOrder.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int) {
        var amount: Int = myOrder[position].second.amount!!.toInt()
        holder.foodName.text = myOrder[position].second.foodName.toString()
        holder.foodSize.text = myOrder[position].second.foodSizeName.toString()
        holder.amount.text = myOrder[position].second.amount.toString()
        holder.price.text = myOrder[position].second.price.toString() + " ฿"

        Picasso.get().load(myOrder[position].second.picture).into(holder.picture)
//        context.applicationInfo.a
        t += myOrder[position].second.price!! * myOrder[position].second.amount!!

        holder.add.setOnClickListener {
            amount = addAmount(amount)
            mRootRef.child("temp/${mAuth.currentUser!!.uid}/select/${myOrder[position].first}/amount").setValue(amount)
        }
        holder.remove.setOnClickListener {
            amount = removeAmount(amount)
            mRootRef.child("temp/${mAuth.currentUser!!.uid}/select/${myOrder[position].first}/amount").setValue(amount)

        }

        if (myOrder.size == position + 1) {
            total.value = t
        }
    }
    fun removeAt(position: Int) {
        val key = myOrder[position].first
        myOrder.removeAt(position)
        notifyItemRemoved(position)
        Delay(key).execute()

    }

    private fun addAmount(a: Int): Int {
        var amount: Int = a
        if (checkAmount(amount)) {
            if (amount != 99)
                amount = ++amount
        }
        return amount
    }

    private fun removeAmount(a: Int): Int {
        var amount: Int = a
        if (checkAmount(amount)) {
            if (amount != 1)
                amount = --amount
        }
        return amount
    }

    private fun checkAmount(amount: Int): Boolean {
        if (amount in 1..99) {
            return true
        }
        return false
    }

    fun setData(myOrder: ArrayList<Pair<String, Select>>) {
        this.t = 0.0
        this.total.value = 0.0
        this.myOrder = myOrder
        notifyDataSetChanged()
    }

    fun getTotal(): MutableLiveData<Double> {
        return total
    }


    inner class MyOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var price = view.price_MO as TextView
        var foodSize = view.foodSize_MO as TextView
        var foodName = view.foodName_MO as TextView
        var amount = view.amount_MO as TextView
        var picture = view.picture_MO as ImageView
        var add = view.addAmount_MO as ImageView
        var remove = view.removeAmount_MO as ImageView

    }
    inner class Delay(var key: String): AsyncTask<Void,Void,Void>(){
        override fun doInBackground(vararg p0: Void?): Void? {
            Thread.sleep(1000)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            mRootRef.child("temp/${mAuth.currentUser!!.uid}/select/$key").setValue(null)
        }

    }

}