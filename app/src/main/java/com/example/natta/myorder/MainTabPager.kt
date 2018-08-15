package com.example.natta.myorder

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainTabPager(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                DashboardFragment()
            }
            1 -> {
                OrderFragment()
            }
            2 -> {
                NotificationFragment()
            }
            else -> {
                DashboardFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

}