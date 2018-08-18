package com.example.natta.myorder.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.natta.myorder.data.Address
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.data.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RTDBRepository {
    private var mRootRef = FirebaseDatabase.getInstance().reference
    private var mCustomerLiveData = MutableLiveData<Customer>()
    private var mOrderHistory = MutableLiveData<List<Order>>()
    private var uid = FirebaseAuth.getInstance().uid

    fun getCustomer(uid: String): MutableLiveData<Customer> {
        val mCustomerRef = mRootRef.child("customer").child(uid)
        mCustomerRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val firstName = p0.child("firstName").getValue(String::class.java)
                val lastName = p0.child("lastName").getValue(String::class.java)
                val birthday = p0.child("birthday").getValue(String::class.java)
                val phoneNumber = p0.child("phoneNumber").getValue(String::class.java)
                val picture = p0.child("picture").getValue(String::class.java)
                val personID = p0.child("personID").getValue(String::class.java)
                val address = p0.child("address").child("address").getValue(String::class.java)
                val subDistrict = p0.child("address").child("subDistrict").getValue(String::class.java)
                val district = p0.child("address").child("district").getValue(String::class.java)
                val province = p0.child("address").child("province").getValue(String::class.java)
                val postalCode = p0.child("address").child("postalCode").getValue(String::class.java)

                val mCustomer = Customer(firstName, lastName, birthday, phoneNumber, picture, personID, Address(address, subDistrict, district, province, postalCode))

                mCustomerLiveData.postValue(mCustomer)

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
}

