package com.example.natta.myorder.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.natta.myorder.view.home.HomeFragment
import com.example.natta.myorder.view.notification.NotificationFragment
import com.example.natta.myorder.view.order.OrderFragment

class MainTabPager(fm: FragmentManager?, private var bundle: Bundle) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {

        when (position) {
            0 -> {
                val homeFragment = HomeFragment()
                homeFragment.arguments = bundle
                return homeFragment
            }
            1 -> {
                return OrderFragment()
            }
            2 -> {
                return NotificationFragment()
            }
            else -> {
                return HomeFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

}