package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.data.Order
import com.example.natta.myorder.repository.RTDBRepository

class OrderHistoryViewModel : ViewModel() {
    private var mRTDBRepository = RTDBRepository()
    private var mCustomer = MutableLiveData<Customer>()
    private var mOrder = MutableLiveData<List<Order>>()
//    fun getCustomer(): MutableLiveData<Customer> {
//        var mAuth = FirebaseAuth.getInstance()
//        mCustomer = mRTDBRepository.getCustomer("cus-test1")
//        return mCustomer
//    }

    fun getOrder(): MutableLiveData<List<Order>> {
        mOrder = mRTDBRepository.getOrderHistory()
        return mOrder
    }
}