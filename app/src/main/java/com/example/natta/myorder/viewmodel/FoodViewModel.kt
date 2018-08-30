package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.repository.FoodRTDBRepository

class FoodViewModel : ViewModel() {
    private var mFood = MutableLiveData<List<Food>>()
    private var mRTDBRepository = FoodRTDBRepository()
    private var food: Pair<MutableLiveData<List<String>>, MutableLiveData<List<Food>>>? = null


    fun getFood(resID: String = "123"): MutableLiveData<List<Food>> {
//        mRTDBRepository.getFood(resID)?.observeForever {
//            if (it != null) {
//                mFood = it.second
//            }
//
//        }
        mFood = mRTDBRepository.getFood(resID)
        return mFood
    }
}