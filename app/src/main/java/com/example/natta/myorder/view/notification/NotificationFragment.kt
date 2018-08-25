package com.example.natta.myorder.view.notification


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.natta.myorder.R
import kotlinx.android.synthetic.main.fragment_notification.view.*


class NotificationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        val adapter = NotificationAdapter()
        view.recyclerView_notiFm.adapter = adapter
        view.recyclerView_notiFm.layoutManager = LinearLayoutManager(view.context)
        return view
    }


}
