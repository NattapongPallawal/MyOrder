package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.repository.FoodRTDBRepsitory

class FoodViewModel : ViewModel() {
    private var mFood = MutableLiveData<List<Food>>()
    private var mRTDBRepository = FoodRTDBRepsitory()


    fun getFood(resID: String = "123"): MutableLiveData<List<Food>> {
        mFood = mRTDBRepository.getFood(resID)
        return mFood
    }
}