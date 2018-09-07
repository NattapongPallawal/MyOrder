package com.example.natta.myorder.repository

class SetDatabaseRepository {
//    private var mRootRef = FirebaseDatabase.getInstance().reference
//    private var mAuth = FirebaseAuth.getInstance()
//
//    private var restaurantOwner = RestaurantOwner()
//    private var employee = Employee()
//    private var customer = Customer()
//    private var restaurant = Restaurant()
//    private var restaurantTime = RestaurantTime()
//    private var menu = Menu()
//
//
//    fun setDatabase() {
//        mAuth.createUserWithEmailAndPassword("restaurant-owner@hotmail.com", "123456").addOnCompleteListener { createUser ->
//            if (createUser.isSuccessful) {
//                mRootRef.child("restaurant-owner/${mAuth.uid!!}").setValue(restaurantOwner).addOnCompleteListener { createUserRTDB ->
//                    if (createUserRTDB.isSuccessful) {
//                        if (mRootRef.child("restaurant").setValue(restaurant).isComplete) {
//                            mRootRef.child("restaurant").orderByChild("owner").equalTo(mAuth.uid).addListenerForSingleValueEvent(object : ValueEventListener {
//                                override fun onCancelled(p0: DatabaseError) {
//
//                                }
//
//                                override fun onDataChange(p0: DataSnapshot) {
//                                    mRootRef.child("menu${p0.key}").setValue(menu)
//                                }
//
//                            })
//                        }
//
//                    }
//
//                }
//            }
//
//
//        }
//    }
//
//    private fun setCustomer() {
//        mAuth.createUserWithEmailAndPassword("customer@hotmail.com", "123456").addOnCompleteListener {
//            if (it.isSuccessful) {
//                mRootRef.child("customer/${mAuth.uid!!}").setValue(customer)
//            }
//        }
//    }
//
//    private fun setEmployee() {
//        mAuth.createUserWithEmailAndPassword("employee@hotmail.com", "123456").addOnCompleteListener {
//            if (it.isSuccessful) {
//                mRootRef.child("employee/${mAuth.uid!!}").setValue(employee)
//            }
//        }
//    }

}