package com.example.natta.myorder.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.natta.myorder.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RTDBRepository {
    private var mRootRef = FirebaseDatabase.getInstance().reference
    private var mAuth = FirebaseAuth.getInstance()
    private var mCustomerLiveData = MutableLiveData<Customer>()
    private var mRestaurant = MutableLiveData<List<Restaurant>>()
    private var mOrderHistory = MutableLiveData<List<Order>>()
    private var uid = FirebaseAuth.getInstance().currentUser!!.uid
    private var mFeedback = MutableLiveData<List<Feedback>>()
    private var mOneCustomer = Customer()
    private var mFood = MutableLiveData<List<Food>>()
    private val mCustomerRef = mRootRef.child("customer").child(this.uid!!)
    private var mEmail = " "

    fun getUID(): String {
        return this.uid
    }
    fun getCustomerLogin(): MutableLiveData<Customer> {
        mCustomerRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                mCustomerLiveData.value = p0.getValue(Customer::class.java)
            }
        })
        return mCustomerLiveData
    }

    fun getEmail(): String {
        mEmail = mAuth.currentUser?.email.toString()

        return mEmail
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

                p0.children.forEach {
                    feedbackList.add(it.getValue(Feedback::class.java)!!)
                }
                mFeedback.value = feedbackList
            }

        })
        return mFeedback
    }

    fun getFood(resID: String): MutableLiveData<List<Food>> {
        val mFoodRef = mRootRef.child("menu")
        mFoodRef.addValueEventListener(object : ValueEventListener {
            var foodList = mutableListOf<Food>()
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    val food = it.getValue(Food::class.java)
                    Log.d("foodddd", "${food?.foodName}")
                }
                mFood.value = foodList
            }

        })
        Log.d("foodddd", "${mFood.value?.get(0)?.foodName}")
        return mFood

    }

    fun setCustomer(customer: Customer) {
        mCustomerRef.setValue(customer).addOnFailureListener {
            throw Exception(it.message)
        }
    }

}

