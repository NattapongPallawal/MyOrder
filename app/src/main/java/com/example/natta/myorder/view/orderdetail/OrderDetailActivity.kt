package com.example.natta.myorder.view.orderdetail

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.viewmodel.OrderDetailViewModel
import com.rakshakhegde.stepperindicator.StepperIndicator
import kotlinx.android.synthetic.main.activity_order_detail.*
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailActivity : AppCompatActivity() {

    private var orderKey: String = ""
    private lateinit var model: OrderDetailViewModel

    @SuppressLint("SetTextI18n")
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
                orderNum_OD.text = "#"+it.orderNumber.toString()
                resName_OD.text = it.restaurantName.toString()
                date_OD.text = convertDate(it.date as Long)
                if (it.table != null) {
                    table_OD.visibility = View.VISIBLE
                    table_OD.text = it.table
                    table_ODT.visibility = View.VISIBLE
                } else {
                    table_OD.visibility = View.GONE
                    table_ODT.visibility = View.GONE
                }
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
            if (it != null){
                adapterFood.setData(it)
            }

        })
        Toast.makeText(applicationContext, orderKey, Toast.LENGTH_LONG).show()


//        val adapterStatus = StatusAdapter()
//        recyclerView_status.adapter = adapterStatus
//        recyclerView_status.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        recyclerView_status.setHasFixedSize(true)


    }

    @SuppressLint("SimpleDateFormat")
    private fun convertDate(t: Long): String {
//        SimpleDateFormat("ddMMyyyy HH:mm:ss").format(Date(1536671117304)).toString()
        val d = Date(t)
        val orderDate = SimpleDateFormat("ddMMyyyy").format(d).toLong()
        val today = SimpleDateFormat("ddMMyyyy").format(Date()).toLong()
        val fmD = SimpleDateFormat("dd")
        val fmM = SimpleDateFormat("MM")
        val fmY = SimpleDateFormat("yyyy")
        val fmT = SimpleDateFormat("HH:mm")

        val day = fmD.format(d)
        val month = fmM.format(d)
        val year = (fmY.format(d).toInt() + 543).toString()
        val time = fmT.format(d)



        return if (orderDate == today) {
            "วันนี้ เวลา $time"
        } else {
            "วันที่ $day/$month/$year เวลา $time"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
