package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.data.Select
import com.example.natta.myorder.repository.FoodRTDBRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FoodViewModel : ViewModel() {
    private var mFood = MutableLiveData<ArrayList<Pair<String, Food>>>()
    private var mRTDBRepository = FoodRTDBRepository()

    private var type = MutableLiveData<ArrayList<String>>()
    private var mRootRef = FirebaseDatabase.getInstance().reference
    private var mAuth = FirebaseAuth.getInstance()

    private var mFoodSize = MutableLiveData<Pair<String, String>>()
    private var mFoodType = MutableLiveData<Pair<String, String>>()
    private var selectFood = arrayListOf<Pair<String, Food>>()


    private var resKey: String = ""
    private var foodKey: String = ""

    fun setResKey(resKey: String) {
        this.resKey = resKey

    }

    fun getFood(resID: String): MutableLiveData<ArrayList<Pair<String, Food>>>? {
        mFood = mRTDBRepository.getFood(resID)!!
        return mFood
    }

    fun getMenuType(resID: String): MutableLiveData<ArrayList<String>> {
        type = mRTDBRepository.getMenuType(resID)

        return type
    }

    fun addOrderFood(position: Int) {
        foodKey = selectFood[position].first
        val selectRef = mRootRef.child("temp/${mAuth.currentUser!!.uid}/select/${System.currentTimeMillis()}")
        val food = Select(1,
                foodKey,
                mFoodSize.value!!.first,
                mFoodType.value?.first,
                resKey,
                selectFood[position].second.foodName,
                mFoodType.value?.second,
                mFoodSize.value!!.second,
                selectFood[position].second.price,
                selectFood[position].second.picture)
        selectRef.setValue(food)
    }

    fun getFoodSize(position: Int) {
        val fSize = mRootRef.child("foodSize")
                .child(resKey)
                .child(selectFood[position].first)
                .orderByKey()
                .limitToFirst(1)

        Log.d("foodSize", "res : $resKey , food : $foodKey")
        fSize.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val foodSize: Pair<String, String> = Pair(p0.children.first().key.toString(), p0.children.first().value.toString())
                mFoodSize.value = foodSize
            }
        })
    }

    fun getFoodType(position: Int) {
        val type = mRootRef.child("foodType")
                .child(resKey)
                .child(selectFood[position].first)
                .orderByChild("available")
                .equalTo(true)
                .limitToFirst(1)
        Log.d("checkKeyFoodType",selectFood[position].first)

        type.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                try {
                    val foodType: Pair<String, String> = (Pair(p0.children.first().key.toString(), p0.children.first().child("name").value.toString()))
                    mFoodType.value = foodType
                }catch (e:NoSuchElementException){
                    mFoodType.value = null
                }

            }

        })
    }

    fun setSelectFood(selectFood: ArrayList<Pair<String, Food>>) {
        this.selectFood = selectFood
    }


}


