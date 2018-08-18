package com.example.natta.myorder.data

class Order constructor(var orderNumber: Int? = -1,
                        var total: Double? = -1.0,
                        var status: String? = "",
                        var date: String? = "",
                        var restaurantID: String? = "") {
}