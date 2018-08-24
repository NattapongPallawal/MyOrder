package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.ViewModel

class FoodDetailViewModel : ViewModel() {

    private var amount: Int = 2

    fun getAmount(): Int {
        return amount
    }

    fun addAmount(): String {
        if (checkAmount()) amount = ++amount
        return amount.toString()
    }

    fun removeAmount(): String {
        if (checkAmount()) amount = --amount
        return amount.toString()
    }

    private fun checkAmount(): Boolean {
        if (amount in 1..99) {
            return true
        }
        return false
    }
}