package com.example.natta.myorder.view.order


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.natta.myorder.R
import com.example.natta.myorder.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_order.view.*

class OrderFragment : Fragment() {
    private lateinit var model: OrderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        model = ViewModelProviders.of(this).get(OrderViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_order, container, false)
        val adapter = OrderAdapter()
        view.recyclerView_orderFm.adapter = adapter
        view.recyclerView_orderFm.layoutManager = LinearLayoutManager(view.context)

        model.getOrder().observe(this, Observer {
            if (it != null ){
                adapter.setData(it)
            }
        })

        return view
    }


}
