package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.natta.myorder.data.Select
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyOrderViewModel :ViewModel() {
    private var mRootRef = FirebaseDatabase.getInstance().reference
    private var mAuth = FirebaseAuth.getInstance()
    private var myOrder = MutableLiveData<ArrayList<Pair<String,Select>>>()
    private var resKey: String = ""

    fun getMyOrder(resID : String): MutableLiveData<ArrayList<Pair<String, Select>>> {
        val orderRef = mRootRef.child("temp/${mAuth.currentUser!!.uid}/select").orderByChild("resID").equalTo(resID)
        orderRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val temp = arrayListOf<Pair<String,Select>>()
                p0.children.forEach {
                    Log.d("getMyorder", it.getValue(Select::class.java)?.foodID.toString())
                    temp.add(Pair(it.key.toString(), it.getValue(Select::class.java)!!))
                }
             myOrder.value = temp
            }

            override fun onCancelled(p0: DatabaseError) {

            }


        })
        return myOrder
    }
    fun deleteSelectFoodAll(resID : String) {
        myOrder.value!!.forEach {
            val orderRef = mRootRef.child("temp/${mAuth.currentUser!!.uid}/select/${it.first}")
            orderRef.removeValue()
        }


    }
}