package com.example.natta.myorder.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.data.Feedback
import com.example.natta.myorder.data.Order
import com.example.natta.myorder.data.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RTDBRepository {
    private var mRootRef = FirebaseDatabase.getInstance().reference
    private var mCustomerLiveData = MutableLiveData<Customer>()
    private var mRestaurant = MutableLiveData<List<Restaurant>>()
    private var mOrderHistory = MutableLiveData<List<Order>>()
    private var uid = FirebaseAuth.getInstance().uid
    private var mFeedback = MutableLiveData<List<Feedback>>()
    private var mOneCustomer = Customer()


    fun getCustomerLogin(): MutableLiveData<Customer> {
        val mCustomerRef = mRootRef.child("customer").child(this.uid!!)
        mCustomerRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                mCustomerLiveData.value = p0.getValue(Customer::class.java)
            }
        })
        return mCustomerLiveData
    }

    fun getOrderHistory(): MutableLiveData<List<Order>> {
        val mOrderHistoryRef = mRootRef.child("order").child("cus-test1")
        mOrderHistoryRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val orderList = mutableListOf<Order>()
                p0.children.forEach {
                    orderList.add(it.getValue(Order::class.java)!!)
                }
                mOrderHistory.value = orderList
            }
        })
        return mOrderHistory
    }

    fun getRestaurant(): MutableLiveData<List<Restaurant>> {
        val mRestaurantRef = mRootRef.child("restaurant")
        mRestaurantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val restaurantList = mutableListOf<Restaurant>()
                p0.children.forEach {
                    restaurantList.add(it.getValue(Restaurant::class.java)!!)
                }
                mRestaurant.value = restaurantList
            }
        })
        return mRestaurant

    }

    fun getFeedback(resID: String): MutableLiveData<List<Feedback>> {
        val mFeedbackRef = mRootRef.child("feedback").child(resID) // resID
        mFeedbackRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val feedbackList = mutableListOf<Feedback>()
//                var cus: Customer
                p0.children.forEach {
                    feedbackList.add(it.getValue(Feedback::class.java)!!)
                  //  getOneCustomer(feedbackList.last().customer!!)
//                    cus = mOneCustomer
//                    feedbackList.last().customerObj = cus

                }
                mFeedback.value = feedbackList
            }

        })
        return mFeedback
    }

//    fun getOneCustomer(uid: String) {
//        val mCustomerRef = mRootRef.child("customer").equalTo(uid)
//        val listener = object : ValueEventListener {
//
//            override fun onCancelled(p0: DatabaseError) {}
//            override fun onDataChange(p0: DataSnapshot) {
//                mOneCustomer.firstName = p0.child("firstName").getValue(String::class.java)
//                Log.d("CheckCustomer", "${mOneCustomer.firstName}")
//            }
//        }
//        mCustomerRef.addListenerForSingleValueEvent(listener)
//        mCustomerRef.removeEventListener(listener)
//        Log.d("CheckCustomer11", "${mOneCustomer.firstName}")
//
//    }
}

