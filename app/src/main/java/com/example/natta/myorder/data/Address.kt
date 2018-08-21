package com.example.natta.myorder.data

import android.os.Parcel
import android.os.Parcelable

data class Address constructor(var address: String? = "",
                               var subDistrict: String? = "",
                               var district: String? = "",
                               var province: String? = "",
                               var postalCode: String? = "",
                               var longitude: Double? = 0.0,
                               var latitude: Double? = 0.0) : Parcelable {
    constructor(parcel: Parcel) : this(
            address = parcel.readString(),
            subDistrict = parcel.readString(),
            district = parcel.readString(),
            province = parcel.readString(),
            postalCode = parcel.readString(),
            longitude = parcel.readValue(Double::class.java.classLoader) as? Double,
            latitude = parcel.readValue(Double::class.java.classLoader) as? Double) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeString(subDistrict)
        parcel.writeString(district)
        parcel.writeString(province)
        parcel.writeString(postalCode)
        parcel.writeValue(longitude)
        parcel.writeValue(latitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(parcel: Parcel): Address {
            return Address(parcel)
        }

        override fun newArray(size: Int): Array<Address?> {
            return arrayOfNulls(size)
        }
    }
}