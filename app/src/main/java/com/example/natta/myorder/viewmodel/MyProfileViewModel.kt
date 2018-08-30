package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.repository.RTDBRepository

class MyProfileViewModel : ViewModel() {
    private var mCustomer = MutableLiveData<Customer>()
    private var mRTDBRepository = RTDBRepository()
    private var mEmail = " "
    private var customer = Customer()
    private var uid = ""


    fun getCustomer(): MutableLiveData<Customer> {
        mCustomer = mRTDBRepository.getCustomerLogin()
        return mCustomer
    }

    fun getEmail(): String {
        mEmail = mRTDBRepository.getEmail()
        return mEmail
    }
    fun setCustomer(customer: Customer){
        this.customer = customer
        mRTDBRepository.setCustomer(this.customer)
    }

    fun getUID(): String {
        uid = mRTDBRepository.getUID()
        return uid
    }
}