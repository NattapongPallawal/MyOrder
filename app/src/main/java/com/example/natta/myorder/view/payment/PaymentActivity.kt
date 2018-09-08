package com.example.natta.myorder.view.payment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.natta.myorder.R
import com.example.natta.myorder.view.MainActivity
import com.example.natta.myorder.viewmodel.PaymentViewModel
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {
    private var model = PaymentViewModel()
    private var resKey = ""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        resKey = intent.getStringExtra("resKey")
        model = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        val adapter = PaymentAdapter(this)
        list_payment.adapter = adapter
        list_payment.layoutManager = LinearLayoutManager(this)

        model.getRestaurant(resKey).observe(this, Observer {
            if (it != null) {
                res_name_PM.text = it.restaurantName.toString()
            }
        })

        model.getMyOrder(resKey).observe(this, Observer {
            if (it != null) {
                if (it.isNotEmpty()) {
                    if (it.first().second.table == null) {
                        textView_table_PM.visibility = View.GONE
                        table_PM.visibility = View.GONE
                    } else {
                        textView_table_PM.visibility = View.VISIBLE
                        table_PM.visibility = View.VISIBLE
                    }
                }
                orderAmount_PM.text = it.size.toString() + " รายการ"
                total_PM.text = model.getTotal().toString() + " ฿"

                adapter.setData(it)
            }
        })

        btn_payment_PM.setOnClickListener {
            val i = Intent(applicationContext, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            //startActivity(i)
            model.addOrder()

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
