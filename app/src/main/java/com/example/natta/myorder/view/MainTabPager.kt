package com.example.natta.myorder.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.natta.myorder.view.home.HomeFragment
import com.example.natta.myorder.view.notification.NotificationFragment
import com.example.natta.myorder.view.order.OrderFragment

class MainTabPager(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                OrderFragment()
            }
            2 -> {
                NotificationFragment()
            }
            else -> {
                HomeFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

}