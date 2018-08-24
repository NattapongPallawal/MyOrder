package com.example.natta.myorder.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.natta.myorder.data.Food
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FoodRTDBRepsitory {
    private var mRootRef = FirebaseDatabase.getInstance().reference

    private var mFood = MutableLiveData<List<Food>>()
    fun getFood(resID: String): MutableLiveData<List<Food>> {
        val mFoodRef = mRootRef.child("menu")
        mFoodRef.addValueEventListener(object : ValueEventListener {
            var foodList = mutableListOf<Food>()
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    val food = it.getValue(Food::class.java)!!
                    Log.d("food456","${food.foodName}")
                    foodList.add(food)
                }
                mFood.value = foodList
            }

        })
        Log.d("food123","${mFood.value?.get(0)?.foodName}")
        return mFood

    }

}