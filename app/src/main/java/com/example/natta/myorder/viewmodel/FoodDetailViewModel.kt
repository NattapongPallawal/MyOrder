package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.natta.myorder.data.Select
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FoodDetailViewModel : ViewModel() {
    private var mRootRef = FirebaseDatabase.getInstance().reference

    private var amount: Int = 2

    private var mFoodSize = MutableLiveData<ArrayList<Pair<String, String>>>()
    private var mFoodType = MutableLiveData<ArrayList<Pair<String, String>>>()
    private var resKey: String = ""
    private var foodKey: String = ""
    private var positionFoodType: Int = 0
    private var positionFoodSize: Int = 0


    fun addOrderFood() {
        val foodRef = mRootRef.child("temp/userID/select/$resKey-$foodKey")
        if (positionFoodType != -1 && positionFoodSize != -1) {

            val food = Select(amount,
                    foodKey,
                    mFoodSize.value!![positionFoodSize].first,
                    mFoodType.value!![positionFoodType].first,
                    resKey)

            foodRef.setValue(food)
        } else if (positionFoodSize == -1) {
            throw  IndexOutOfBoundsException("กรุณาเลือก ขนาดอาหาร")
        } else if (positionFoodType == -1) {
            throw  IndexOutOfBoundsException("กรุณาเลือก ประเภทอาหาร")
        } else {
            throw  IndexOutOfBoundsException("ERROR")

        }

    }

    fun getAmount(): Int {
        return amount
    }

    fun setResFoodKey(resKey: String, foodKey: String) {
        this.resKey = resKey
        this.foodKey = foodKey


    }

    fun setPositionFoodSize(positionFoodSize: Int) {
        this.positionFoodSize = positionFoodSize
    }

    fun setPositionFoodType(positionFoodType: Int) {
        this.positionFoodType = positionFoodType
    }

    fun addAmount(): String {
        if (checkAmount()) amount = ++amount
        return amount.toString()
    }

    fun removeAmount(): String {
        if (checkAmount()) amount = --amount
        return amount.toString()
    }

    private fun checkAmount(): Boolean {
        if (amount in 1..99) {
            return true
        }
        return false
    }

    fun getFoodSize(): MutableLiveData<ArrayList<Pair<String, String>>> {
        val fSize = mRootRef.child("foodSize").child(this.resKey).child(this.foodKey)
        Log.d("foodSize", "res : $resKey , food : $foodKey")
        fSize.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val foodSize = arrayListOf<Pair<String, String>>()
                p0.children.forEach {
                    foodSize.add(Pair(it.key.toString(), it.value.toString()))
                }
                foodSize.forEach {
                    Log.d("foodSize", it.first)
                }
                mFoodSize.value = foodSize
            }
        })

        return mFoodSize
    }

    fun getFoodType(): MutableLiveData<ArrayList<Pair<String, String>>> {
        val type = mRootRef.child("foodType").child(resKey).child(foodKey).orderByChild("available").equalTo(true)
        type.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val foodType = arrayListOf<Pair<String, String>>()
                p0.children.forEach {
                    foodType.add(Pair(it.key.toString(), it.child("name").value.toString()))
                }
                mFoodType.value = foodType

            }

        })

        return mFoodType
    }
}