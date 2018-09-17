package com.example.natta.myorder.view.orderdetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.viewmodel.OrderDetailViewModel
import com.rakshakhegde.stepperindicator.StepperIndicator
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : AppCompatActivity() {

    private var orderKey: String = ""
    private lateinit var model: OrderDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        model = ViewModelProviders.of(this).get(OrderDetailViewModel::class.java)
        orderKey = intent.getStringExtra("orderKey")
        model.getOrder(orderKey).observe(this, Observer {
            if (it != null) {
                stepperIndicator.stepCount = it.numStatus!!
                stepperIndicator.currentStep = it.currentStatus!!
                status_OD.text = it.status.toString()
            }

        })

        val adapterFood = FoodAdapter()
        recycleView_OD.adapter = adapterFood
        recycleView_OD.layoutManager = LinearLayoutManager(this)
        recycleView_OD.setHasFixedSize(true)

        val indicator = findViewById<StepperIndicator>(R.id.stepperIndicator)
//        val a = arrayOf("AAAAAAAAAAA","BBBBBBBBB","CCCCCCCCCCC","DDDDDDDDDD","EEEEEEEEEEEEEE")
//        indicator.setLabels(a)
        model.getMenu().observe(this, Observer {
            if (it != null)
                adapterFood.setData(it)
        })
        Toast.makeText(applicationContext, orderKey, Toast.LENGTH_LONG).show()

//        val adapterStatus = StatusAdapter()
//        recyclerView_status.adapter = adapterStatus
//        recyclerView_status.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        recyclerView_status.setHasFixedSize(true)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
