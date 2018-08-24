package com.example.natta.myorder.data

import android.os.Parcel
import android.os.Parcelable

class Food constructor(var foodName: String? = "",
                       var price: Double? = 0.0,
                       var picture: String? = "",
                       var restaurantID: String? = "",
                       var available: Boolean? = true,
                       var rete: Double? = 0.0) : Parcelable {
    constructor(parcel: Parcel) : this(
            foodName = parcel.readString(),
            price = parcel.readValue(Double::class.java.classLoader) as? Double,
            picture = parcel.readString(),
            restaurantID = parcel.readString(),
            available = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            rete = parcel.readValue(Double::class.java.classLoader) as? Double) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(foodName)
        parcel.writeValue(price)
        parcel.writeString(picture)
        parcel.writeString(restaurantID)
        parcel.writeValue(available)
        parcel.writeValue(rete)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food {
            return Food(parcel)
        }

        override fun newArray(size: Int): Array<Food?> {
            return arrayOfNulls(size)
        }
    }

}
