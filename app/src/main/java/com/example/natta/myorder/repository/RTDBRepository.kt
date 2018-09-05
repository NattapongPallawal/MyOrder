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
    private var mNoti = MutableLiveData<List<Notification>>()
    private var mOrderHistory = MutableLiveData<List<Order>>()
    private var uid = FirebaseAuth.getInstance().currentUser!!.uid
    private var mFeedback = MutableLiveData<List<Feedback>>()
    private var mOneCustomer = Customer()
    private var mFood = MutableLiveData<List<Food>>()
    private val mCustomerRef = mRootRef.child("customer").child(this.uid)
    private var mEmail = " "


    fun getUID(): String {
        return this.uid
    }

    fun getNotification(): MutableLiveData<List<Notification>> {
        val mNotiRef = mRootRef.child("notificationCustomer").orderByChild("customer").equalTo("cus-test1") // uid
        mNotiRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val noti = arrayListOf<Notification>()
                var notification: Notification
                p0.children.forEach {
                    var i = 0
                    notification = it.getValue(Notification::class.java)!!
//                    notification.setOrderObj(getOrder(notification.order, notification.restaurant))
//                    notification.setRestaurantObj(getOneRestaurant(notification.restaurant))
//                    getOneRestaurant(i, notification.restaurant)
                    noti.add(i, it.getValue(Notification::class.java)!!)
                    ++i

                }
                mNoti.value = noti
            }

        })
        return mNoti
    }

//    private fun getOneRestaurant(index: Int, restaurant: String): MutableLiveData<Restaurant> {
//        val mResRef = mRootRef.child("restaurant").child(restaurant)
//        var res = MutableLiveData<Restaurant>()
//        mResRef.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                var i = p0.getValue(Restaurant::class.java)!!
//                res.value = i
//                mNoti.value!![index].setRestaurantObj(res)
//                Log.d("getOneRestaurant", "${res.value?.restaurantName}")
//            }
//
//        })
//        return res
//    }

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

//    fun getOrder(id: String, resID: String): MutableLiveData<Order> {
//        val orderRef = mRootRef.child("order").child("cus-test1").child("order1-test1")
//        var order = MutableLiveData<Order>()
//        orderRef.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                order.value = p0.getValue(Order::class.java)!!
//            }
//
//        })
//        return order
//    }

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
                    val res = it.getValue(Restaurant::class.java)!!
                    res.setKey(it.key!!)
                    restaurantList.add(res)


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
        mCustomerRef.setValue(customer)
                .addOnFailureListener {
                    throw Exception(it.message)
                }
                .addOnSuccessListener {

                }

    }
}

