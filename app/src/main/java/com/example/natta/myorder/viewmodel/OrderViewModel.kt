package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.natta.myorder.data.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderViewModel : ViewModel() {
    private var mRootRef = FirebaseDatabase.getInstance().reference
    private var mAuth = FirebaseAuth.getInstance()
    private var mOrder = MutableLiveData<ArrayList<Pair<String, Order>>>()

    fun getOrder(): MutableLiveData<ArrayList<Pair<String, Order>>> {
        val ref = mRootRef.child("order/${mAuth.currentUser!!.uid}")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val orderTemp = arrayListOf<Pair<String, Order>>()
                p0.children.forEach {
                    val key = it.key!!
                    val order = it.getValue(Order::class.java)!!
                    orderTemp.add(Pair(key, order))

                }
                mOrder.value = orderTemp
            }

        })

        return mOrder

    }
}