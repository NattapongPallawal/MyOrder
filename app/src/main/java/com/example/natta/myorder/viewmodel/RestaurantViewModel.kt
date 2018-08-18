package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.natta.myorder.data.Restaurant
import com.example.natta.myorder.repository.RTDBRepository

class RestaurantViewModel :ViewModel() {
    private var mRestaurant = MutableLiveData<List<Restaurant>>()
    private var mRTDBRepository = RTDBRepository()

    fun getRestaurant(): MutableLiveData<List<Restaurant>> {
        mRestaurant = mRTDBRepository.getRestaurant()
        return mRestaurant
    }


}