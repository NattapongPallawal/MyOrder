package com.example.natta.myorder.data

data class Customer constructor(var firstName: String? = "",
                                var lastName: String? = "",
                                var birthday: String? = "",
                                var phoneNumber: String? = "",
                                var picture: String? = "",
                                var personID: String? = "",
                                var address: Address?
)