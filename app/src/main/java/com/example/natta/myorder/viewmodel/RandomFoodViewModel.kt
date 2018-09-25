package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.location.Location
import android.util.Log
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.data.Restaurant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RandomFoodViewModel : ViewModel() {

    private var mRootRef = FirebaseDatabase.getInstance().reference
    private var mFood = MutableLiveData<ArrayList<Pair<String, Food>>>()
    private var mRestaurant = MutableLiveData<ArrayList<Pair<Double, Restaurant>>>()
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    var ready = MutableLiveData<Boolean>()
    private var menuListener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            Log.d("randomError", p0.message)

        }

        override fun onDataChange(p0: DataSnapshot) {
            if (p0.value != null) {
                val listFood = arrayListOf<Pair<String, Food>>()
                p0.children.forEach {
                    val key = it.key!!
                    val food = it.getValue(Food::class.java)!!
                    listFood.add(Pair(key, food))
                }
                mFood.value = listFood
            } else {
                ready.value = false
            }
        }

    }

    init {
        ready.value = false
        ready.observeForever {
            if (it!!){
                findMenu()
            }
        }
    }
    fun setLocation(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
        getRestaurant()
        Log.d("randomF","$latitude $longitude")
    }


    private fun getRestaurant() {
        val mRestaurantRef = mRootRef.child("restaurant")
        mRestaurantRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.value != null) {
                    val restaurantList = arrayListOf<Pair<Double, Restaurant>>()
                    p0.children.forEach {
                        val res = it.getValue(Restaurant::class.java)!!
                        res.setKey(it.key!!)
                        val dis = calculateDistance(res.address!!.latitude!!, res.address!!.longitude!!)
                        restaurantList.add(Pair(dis, res))
                        Log.d("randomFRest","${res.getKey()}")
                    }
                    restaurantList.sortWith(Comparator { o1, o2 ->
                        val a = o1.first
                        val b = o2.first
                        when {
                            a > b -> 1
                            a == b -> 0
                            else -> -1
                        }
                    })
                    restaurantList.forEach {
                        Log.d("randomFRRRR", it.second.getKey())
                    }
                    mRestaurant.value = restaurantList
                    ready.value = true

                }
            }
        })
    }

    private fun findMenu() {
        val ref = mRootRef.child("menu/${mRestaurant.value!![0].second.getKey()}")
        ref.addValueEventListener(menuListener)
        Log.d("randomFRest","${mRestaurant.value?.first()?.first}")

//        ready.observeForever {
//            if (!it!!){
//                ref.removeEventListener(menuListener)
//            }
//        }
    }
    fun getMenu(): MutableLiveData<ArrayList<Pair<String, Food>>> {
        return mFood
    }
    fun getRestaurantName(): Restaurant {
        return mRestaurant.value!!.first().second
    }


    private fun calculateDistance(latitude: Double, longitude: Double): Double {
        val resLocation = Location("resLocation")
        resLocation.longitude = longitude
        resLocation.latitude = latitude
        val currentLocation = Location("currentLocation")
        currentLocation.longitude = this.longitude
        currentLocation.latitude = this.latitude
        return (currentLocation.distanceTo(resLocation) / 1000).toDouble()
    }

}