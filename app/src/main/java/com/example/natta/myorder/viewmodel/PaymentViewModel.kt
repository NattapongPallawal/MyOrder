package com.example.natta.myorder.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.natta.myorder.data.Order
import com.example.natta.myorder.data.OrderMenu
import com.example.natta.myorder.data.Restaurant
import com.example.natta.myorder.data.Select
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class PaymentViewModel : ViewModel() {
    private var mRootRef = FirebaseDatabase.getInstance().reference
    private var mAuth = FirebaseAuth.getInstance()
    private var myOrder = MutableLiveData<ArrayList<Pair<String, Select>>>()
    private var restaurant = MutableLiveData<Restaurant>()
    private var total: Double = 0.0
    private var restaurantID: String = ""


    fun getTotal(): Double {
        return total
    }

    @SuppressLint("SimpleDateFormat")
    fun addOrder() {
        val resRef = mRootRef.child("order/${mAuth.currentUser!!.uid}")
        resRef.push().setValue(Order(1, total, "รอรับออเดอร์", ServerValue.TIMESTAMP, restaurantID))
        { p0, p1 ->
            myOrder.value!!.forEach {
                mRootRef.child("order-menu/${p1.key}").push()
                        .setValue(OrderMenu(
                                it.second.foodSizeID,
                                it.second.foodTypeID,
                                it.second.amount,
                                it.second.foodID,
                                it.second.price
                        )) { p0, p1 ->
                            deleteSelectFoodAll()
                        }
            }
        }
        Log.d("dateTimeStamp", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(1536447818219)).toString())
    }

    fun getMyOrder(resID: String): MutableLiveData<ArrayList<Pair<String, Select>>> {
        val orderRef = mRootRef.child("temp/${mAuth.currentUser!!.uid}/select").orderByChild("resID").equalTo(resID)
        orderRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val temp = arrayListOf<Pair<String, Select>>()
                total = 0.0
                p0.children.forEach {
                    temp.add(Pair(it.key.toString(), it.getValue(Select::class.java)!!))
                    val p = it.child("price").getValue(Double::class.java)!!
                    val a = it.child("amount").getValue(Int::class.java)!!
                    total += p * a
                }
                myOrder.value = temp
            }

            override fun onCancelled(p0: DatabaseError) {

            }


        })
        return myOrder
    }


    fun getRestaurant(resID: String): MutableLiveData<Restaurant> {
        this.restaurantID = resID
        val resRef = mRootRef.child("restaurant").orderByKey().equalTo(resID)

        resRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val res = p0.child(resID).getValue(Restaurant::class.java)
                restaurant.value = res
            }

        })

        return restaurant
    }

    private fun deleteSelectFoodAll() {
        myOrder.value!!.forEach {
            val orderRef = mRootRef.child("temp/${mAuth.currentUser!!.uid}/select/${it.first}")
            orderRef.removeValue()
        }
    }
}