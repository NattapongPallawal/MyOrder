package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.natta.myorder.data.Notification
import com.example.natta.myorder.repository.RTDBRepository

class NotificationViewModel : ViewModel() {
    private var mNoti = MutableLiveData<List<Notification>>()
    private var mRTDBRepository = RTDBRepository()


    fun getNotification(): MutableLiveData<List<Notification>> {
        mNoti = mRTDBRepository.getNotification()
        return mNoti
    }
}