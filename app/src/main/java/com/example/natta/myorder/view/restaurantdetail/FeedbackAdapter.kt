package com.example.natta.myorder.view.restaurantdetail

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.data.Feedback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_feedback.view.*

class FeedbackAdapter(var context: Context) : RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {
    private var feedback = listOf<Feedback>()
    private var customerData = RestaurantDetailActivity()
    private var mRootRef = FirebaseDatabase.getInstance().reference

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FeedbackViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_feedback, p0, false)
        return FeedbackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return feedback.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(h: FeedbackViewHolder, @SuppressLint("RecyclerView") p1: Int) {
        val mCustomerRef = mRootRef.child("customer").child(feedback[p1].customer!!)
        val listener = object : ValueEventListener {
            var mOneCustomer : Customer? = null
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                mOneCustomer = p0.getValue(Customer::class.java)
                h.name.text = "${mOneCustomer?.firstName} ${mOneCustomer?.lastName}"
                Picasso.get().load(mOneCustomer?.picture).into(h.image)

            }
        }
        mCustomerRef.addValueEventListener(listener)
        h.title.text = feedback[p1].title.toString()
        h.comment.text = feedback[p1].comment.toString()
        h.rating.rating = feedback[p1].rating!!.toFloat()
    }

    fun setData(feedback: List<Feedback>) {
        this.feedback = feedback
        notifyDataSetChanged()
    }


    inner class FeedbackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.feedbackName_RD as TextView
        var title = view.feedbackTittle_RD as TextView
        var comment = view.feedbackComment_RD as TextView
        var image = view.feedbackImg_RD as ImageView
        var rating = view.feedbackRating_RD as RatingBar
    }
}