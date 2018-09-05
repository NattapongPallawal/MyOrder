package com.example.natta.myorder.view.notification


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.natta.myorder.R
import com.example.natta.myorder.viewmodel.NotificationViewModel
import kotlinx.android.synthetic.main.fragment_notification.view.*


class NotificationFragment : Fragment() {

    private lateinit var model: NotificationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        model = ViewModelProviders.of(this).get(NotificationViewModel::class.java)
        val adapter = NotificationAdapter()
        view.recyclerView_notiFm.adapter = adapter
        view.recyclerView_notiFm.layoutManager = LinearLayoutManager(view.context)

        model.getNotification().observe(this, Observer {
            if (it != null){
                adapter.setData(it)
            }

        })
        return view
    }



}
