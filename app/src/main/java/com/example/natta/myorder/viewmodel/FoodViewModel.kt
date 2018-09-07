package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.repository.FoodRTDBRepository

class FoodViewModel : ViewModel() {
    private var mFood = MutableLiveData<ArrayList<Pair<String, Food>>>()

    private var mRTDBRepository = FoodRTDBRepository()
    private var food: Pair<MutableLiveData<ArrayList<String>>, MutableLiveData<List<Food>>>? = null
    private var type = MutableLiveData<ArrayList<String>>()


    fun getFood(resID: String): MutableLiveData<ArrayList<Pair<String, Food>>>? {
        mFood = mRTDBRepository.getFood(resID)!!
        return mFood
    }

    fun getMenuType(resID: String): MutableLiveData<ArrayList<String>> {
        type =   mRTDBRepository.getMenuType(resID)

        return type
    }


}


