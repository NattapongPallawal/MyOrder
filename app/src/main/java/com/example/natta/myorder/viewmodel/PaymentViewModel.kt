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
    private var orderNumber = MutableLiveData<Int>()
    private var total: Double = 0.0
    private var queue: Int = 0
    private var restaurantID: String = ""
    private var fromRestaurant: Int = -1

    init {
        orderNumber.value = -1
    }

    fun getTotal(): Double {
        return total
    }

    fun setFromRestaurant(fromRestaurant: Int) {

        this.fromRestaurant = fromRestaurant
    }

    @SuppressLint("SimpleDateFormat")
    fun addOrder(selectPromtPay: Boolean) {
        if (fromRestaurant != -1) {
            val resRef = mRootRef.child("order")
            val orderNumberRef = mRootRef.child("temp/orderNumber/$restaurantID")
            resRef.push().setValue(Order(
                    queue + 1,
                    total,
                    if (fromRestaurant == 1) {
                        "รอรับออเดอร์"
                    } else {
                        "รอการชำระเงิน"
                    },
                    ServerValue.TIMESTAMP,
                    restaurantID,
                    if (selectPromtPay) {
                        "promtpay"
                    } else {
                        "cash"
                    },
                    restaurant.value!!.restaurantName,
                    0,
                    if (fromRestaurant == 1) {
                        5
                    } else {
                        6
                    },
                    fromRestaurant == 1,
                    false,
                    myOrder.value!!.size,
                    mAuth.currentUser!!.uid
            ))

            { p0, p1 ->
                myOrder.value!!.forEach {
                    mRootRef.child("order-menu/${p1.key}").push()
                            .setValue(OrderMenu(
                                    it.second.foodSizeID,
                                    it.second.foodTypeID,
                                    it.second.amount,
                                    it.second.foodID,
                                    it.second.price,
                                    it.second.foodName,
                                    it.second.foodTypeName,
                                    it.second.foodSizeName
                            )) { _, _ ->
                                val updateQueue = hashMapOf("queue" to queue + 1, "date" to ServerValue.TIMESTAMP)
                                orderNumberRef.setValue(updateQueue)
                                deleteSelectFoodAll()
                            }
                }
            }
            Log.d("dateTimeStamp", SimpleDateFormat("ddMMyyyy HH:mm:ss").format(Date(1536671117304)).toString())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getOrderNumber() {
        val orderNumberRef = mRootRef.child("temp/orderNumber/$restaurantID")
        val resetQueue = hashMapOf("queue" to 0, "date" to ServerValue.TIMESTAMP)
        val today = SimpleDateFormat("ddMMyyyy").format(Date()).toLong()
        Log.d("orderNumberDateToday", today.toString())
        orderNumberRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                try {
                    val queueTemp = p0.child("queue").getValue(Int::class.java)!!
                    val timeStamp: Long = p0.child("date").getValue(Long::class.java)!!
                    val dateOrder = SimpleDateFormat("ddMMyyyy").format(Date(timeStamp)).toLong()

                    Log.d("orderNumber", "queue = $queue , queueTemp = $queueTemp date = $timeStamp")
                    Log.d("orderNumber2date", "$dateOrder == $today : timeStamp = $timeStamp")
                    if (today == dateOrder) {
                        queue = queueTemp
                        Log.d("orderNumberQueue", queue.toString())
                    } else {
                        orderNumberRef.setValue(resetQueue)
                    }

                } catch (e: KotlinNullPointerException) {
                    orderNumberRef.setValue(resetQueue)
                }
            }
        })
    }

    fun getMyOrder(resID: String): MutableLiveData<ArrayList<Pair<String, Select>>> {
        val orderRef = mRootRef.child("temp/cart/${mAuth.currentUser!!.uid}/select").orderByChild("resID").equalTo(resID)
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
        getOrderNumber()
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
            val orderRef = mRootRef.child("temp/cart/${mAuth.currentUser!!.uid}/select/${it.first}")
            orderRef.removeValue()
        }
    }
}