package com.example.natta.myorder.data

import android.arch.lifecycle.MutableLiveData
import android.os.Parcel
import android.os.Parcelable

class Notification constructor(
        var message: String = "มีออเดอร์ใหม่",
        var date: String = "12/09/2560",
        var order: String = "order1-test1",
        var restaurant: String = "res-test1",
        var customer: String = "cus-test1"

) : Parcelable {

//    private var orderObj = MutableLiveData<Order>()
//    private var restaurantObj = MutableLiveData<Restaurant>()
//
//    fun setOrderObj(order: MutableLiveData<Order>) {
//        this.orderObj = order
//    }
//
//    fun getOrderObj(): MutableLiveData<Order> {
//        return this.orderObj
//    }
//
//    fun setRestaurantObj(res: MutableLiveData<Restaurant>) {
//        this.restaurantObj = res
//    }
//
//    fun getRestaurantObj(): MutableLiveData<Restaurant> {
//        return this.restaurantObj
//    }


    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
        parcel.writeString(date)
        parcel.writeString(order)
        parcel.writeString(restaurant)
        parcel.writeString(customer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Notification> {
        override fun createFromParcel(parcel: Parcel): Notification {
            return Notification(parcel)
        }

        override fun newArray(size: Int): Array<Notification?> {
            return arrayOfNulls(size)
        }
    }
}