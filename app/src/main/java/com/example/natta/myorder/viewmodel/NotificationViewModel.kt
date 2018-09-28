package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.natta.myorder.data.Notification
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationViewModel : ViewModel() {
    private var mNoti = MutableLiveData<List<Notification>>()
    private var mRootRef = FirebaseDatabase.getInstance().reference



    fun getNotification(): MutableLiveData<List<Notification>> {
        val mNotiRef = mRootRef.child("notificationCustomer").orderByChild("customer").equalTo("cus-test1") // uid
        mNotiRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val noti = arrayListOf<Notification>()
                var notification: Notification
                p0.children.forEach {

                    notification = it.getValue(Notification::class.java)!!
//                    notification.setOrderObj(getOrder(notification.order, notification.restaurant))
//                    notification.setRestaurantObj(getOneRestaurant(notification.restaurant))
//                    getOneRestaurant(i, notification.restaurant)
                    noti.add(it.getValue(Notification::class.java)!!)

                }
                mNoti.value = noti
            }

        })
        return mNoti
    }
}