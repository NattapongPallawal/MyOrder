package com.example.natta.myorder.view.myorder

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.example.natta.myorder.R
import com.example.natta.myorder.view.payment.PaymentActivity
import com.example.natta.myorder.viewmodel.MyOrderViewModel
import kotlinx.android.synthetic.main.activity_my_order.*

class MyOrderActivity : AppCompatActivity() {
    private var model = MyOrderViewModel()
    private var resKey: String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)
        resKey = intent.getStringExtra("resKey")
        model = ViewModelProviders.of(this).get(MyOrderViewModel::class.java)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val adapter = MyOrderAdapter(applicationContext)
        recyclerView_MO.adapter = adapter
        recyclerView_MO.layoutManager = LinearLayoutManager(applicationContext)
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView_MO.adapter as MyOrderAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView_MO)



        model.getMyOrder(resKey).observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
                item_food_MO.text = "จำนวน ${it.size} รายการ"

            }
        })

        adapter.getTotal().observe(this, Observer {
            total_MO.text = "ยอดชำระ $it ฿"
        })

        btn_payment_MO.setOnClickListener {
            startActivity(Intent(applicationContext, PaymentActivity::class.java))
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
