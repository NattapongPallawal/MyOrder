package com.example.natta.myorder.view.myorder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.natta.myorder.R
import kotlinx.android.synthetic.main.activity_my_order.*

class MyOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)

        val adapter = MyOrderAdapter(applicationContext)
        recyclerView_MO.adapter = adapter
        recyclerView_MO.layoutManager = LinearLayoutManager(applicationContext)
    }
}
