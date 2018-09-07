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
    private var mFood = MutableLiveData<ArrayList<Pair<String, Food>>>()
    private var listType = MutableLiveData<ArrayList<String>>()
    private var typeValue = MutableLiveData<List<String>>()
    private val type = MutableLiveData<String>()

    fun getFoodSize(FID: String) {
        val mFoodRef = mRootRef.child("foodSize").child(FID)
        mFoodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }

    fun addOrderTemp(){
        val temp = mRootRef.child("temp/userID/select")
    }

    fun getMenuType(resID: String): MutableLiveData<ArrayList<String>> {
        val foodList = getFood(resID)
        foodList?.observeForever { food ->
            val type = arrayListOf<String>()
            food?.forEach {
                if (checkType(type, it.second.type)) {
                    type.add(it.second.type!!)
                    Log.d("TypeFood", it.second.type)
                }
            }
            this.listType.value = type
        }

        return listType
    }


    private fun checkType(type: ArrayList<String>, type1: String?): Boolean {
        type.forEach {
            if (it == type1) {
                return false
            }
        }
        return true
    }

    fun getFood(resID: String): MutableLiveData<ArrayList<Pair<String, Food>>>? {
        val mFoodRef = mRootRef.child("menu").child(resID).orderByChild("available").equalTo(true)

        mFoodRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                val listFood = arrayListOf<Pair<String, Food>>()
                p0.children.forEach {
                    val key = it.key!!
                    val food = it.getValue(Food::class.java)!!
                    listFood.add(Pair(key, food))
                }
                mFood.value = listFood
            }

        })
        return mFood


    }


}