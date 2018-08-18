package com.example.natta.myorder.data

data class Address constructor(var address: String? = "",
                               var subDistrict: String? = "",
                               var district: String? = "",
                               var province: String? = "",
                               var postalCode: String? = "",
                               var longitude: Double? = 0.0,
                               var latitude: Double? = 0.0)