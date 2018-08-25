package com.example.natta.myorder.view.myorder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.natta.myorder.R
import com.example.natta.myorder.view.payment.PaymentActivity
import kotlinx.android.synthetic.main.activity_my_order.*

class MyOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val adapter = MyOrderAdapter(applicationContext)
        recyclerView_MO.adapter = adapter
        recyclerView_MO.layoutManager = LinearLayoutManager(applicationContext)

        btn_payment_MO.setOnClickListener {
            startActivity(Intent(applicationContext,PaymentActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
