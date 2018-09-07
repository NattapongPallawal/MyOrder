package com.example.natta.myorder.data

import android.os.Parcel
import android.os.Parcelable
import javax.sql.StatementEvent


@Suppress("UNREACHABLE_CODE")
class Restaurant constructor(var restaurantName: String? = "",
                             var picture: String? = "",
                             var rating: Double? = 0.0,
                             var owner: String? = "",
                             var promtPayID: String? = "",
                             var timeOpen: String? = "",
                             var timeClose: String? = "",
                             var time : RestaurantTime? = RestaurantTime(),
                             var address: Address? = Address(),
                             var about: String? = "",
                             var phoneNumber: String? = "") : Parcelable {

    private var key : String? = ""

    fun setKey(key : String){
        this.key = key
    }
    fun getKey(): String? {
        return this.key
    }
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(RestaurantTime::class.java.classLoader),
            parcel.readParcelable(Address::class.java.classLoader),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(restaurantName)
        parcel.writeString(picture)
        parcel.writeValue(rating)
        parcel.writeString(owner)
        parcel.writeString(promtPayID)
        parcel.writeString(timeOpen)
        parcel.writeString(timeClose)
        parcel.writeParcelable(time, flags)
        parcel.writeParcelable(address, flags)
        parcel.writeString(about)
        parcel.writeString(phoneNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}