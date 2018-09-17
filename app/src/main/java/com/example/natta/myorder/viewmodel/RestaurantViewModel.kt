package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.natta.myorder.data.Restaurant
import com.example.natta.myorder.repository.RTDBRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RestaurantViewModel : ViewModel() {

    private var mRootRef = FirebaseDatabase.getInstance().reference

    private var mRestaurant = MutableLiveData<ArrayList<Restaurant>>()


    fun getRestaurant(): MutableLiveData<ArrayList<Restaurant>> {
        val mRestaurantRef = mRootRef.child("restaurant")
        mRestaurantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val restaurantList = arrayListOf<Restaurant>()

                p0.children.forEach {
                    val res = it.getValue(Restaurant::class.java)!!
                    res.setKey(it.key!!)
                    restaurantList.add(res)


                }
                mRestaurant.value = restaurantList

            }
        })

        return mRestaurant
    }

}

