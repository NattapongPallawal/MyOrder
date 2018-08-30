package com.example.natta.myorder.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.natta.myorder.data.Food
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FoodRTDBRepository {
    private var mRootRef = FirebaseDatabase.getInstance().reference

    private var mFood = MutableLiveData<List<Food>>()
    private var mKey = MutableLiveData<List<String>>()
    private var pair : MutableLiveData<Pair<MutableLiveData<List<String>>,MutableLiveData<List<Food>>>>? = null

    fun getFood(resID: String): MutableLiveData<List<Food>> {
        val mFoodRef = mRootRef.child("menu").child("res-test1")
        mFoodRef.addValueEventListener(object : ValueEventListener {
            var foodList = mutableListOf<Food>()
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {

                    val food = it.getValue(Food::class.java)!!
                    if (food.available == true) {
                        foodList.add(food)
//                        key.add(it.key.toString())
                        Log.d("foodKey",it.key.toString())
                    }
                }
                mFood.value = foodList
//                mKey.value = key
//                pair?.value?.copy(mKey,mFood)
            }

        })

        return mFood


    }

}