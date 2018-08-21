package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.data.Feedback
import com.example.natta.myorder.repository.RTDBRepository

class RestaurantDetailViewModel : ViewModel() {
    private var mRTDBRepository = RTDBRepository()
    private var mFeedback = MutableLiveData<List<Feedback>>()
    private var mOneCustomer = MutableLiveData<Customer>()


    fun getFeedback(resID: String): MutableLiveData<List<Feedback>> {
        mFeedback = mRTDBRepository.getFeedback(resID)

        return mFeedback
    }
}
//    fun getOneCustomer(uid: String): MutableLiveData<Customer> {
//        mOneCustomer = mRTDBRepository.getOneCustomer(uid)
//        return mOneCustomer
//    }
//}