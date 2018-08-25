package com.example.natta.myorder.view.orderdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.natta.myorder.R

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
