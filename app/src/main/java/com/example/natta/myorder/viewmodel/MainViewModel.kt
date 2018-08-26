package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.repository.RTDBRepository
import com.google.firebase.auth.FirebaseAuth

class MainViewModel : ViewModel() {
    private var mAuth = FirebaseAuth.getInstance()
    private var mRTDBRepository = RTDBRepository()
    private var mCustomerLiveData = MutableLiveData<Customer>()
    private var mEmail = " "


    fun getCustomer(): MutableLiveData<Customer> {
        mCustomerLiveData = mRTDBRepository.getCustomerLogin()
        return mCustomerLiveData
    }
    fun getEmail(): String {
        mEmail = mRTDBRepository.getEmail()
        return mEmail
    }


}