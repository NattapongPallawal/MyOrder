package com.example.natta.myorder.data

class Order constructor(var orderNumber: Int? = null,
                        var total: Double? = null,
                        var status: String? = null,
                        var date: Any? = null,
                        var restaurantID: String? = null,
                        var paymentType: String? = null,
                        var restaurantName: String? = null) {
}