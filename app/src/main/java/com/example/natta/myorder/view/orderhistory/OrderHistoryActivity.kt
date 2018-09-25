package com.example.natta.myorder.view.orderhistory

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Order
import com.example.natta.myorder.viewmodel.OrderHistoryViewModel
import kotlinx.android.synthetic.main.activity_order_history.*

class OrderHistoryActivity : AppCompatActivity() {

    private var orderList: List<Order>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val model = ViewModelProviders.of(this).get(OrderHistoryViewModel::class.java)
        val adapter = OrderHistoryAdapter()
        recycleView_oh.adapter = adapter
        recycleView_oh.layoutManager = LinearLayoutManager(applicationContext)

        model.getOrder().observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
            }
        })


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
