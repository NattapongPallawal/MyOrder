package com.example.natta.myorder.view.favorite

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.example.natta.myorder.R
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.app_bar_main.*

@Suppress("DEPRECATION")
class FavoriteActivity : AppCompatActivity() {
    private val tab = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            val color = ContextCompat.getColor(applicationContext, R.color.colorTab)
            tab!!.icon!!.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            val color = ContextCompat.getColor(applicationContext, R.color.colorTabSelected)
            tab!!.icon!!.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initFavoriteTabPager()
    }

    private fun initFavoriteTabPager() {
        val tabPager = FavoriteFoodTabPager(supportFragmentManager)
        viewPager_fav.adapter = tabPager
        tabLayout_fav.setupWithViewPager(viewPager_fav)
        tabLayout_fav.getTabAt(0)!!.icon = resources.getDrawable(R.drawable.ic_fastfood)
        tabLayout_fav.getTabAt(1)!!.icon = resources.getDrawable(R.drawable.ic_restaurant)
        tabLayout_fav.getTabAt(0)!!.select()
        if (tabLayout_fav.getTabAt(0)!!.isSelected) {
            val color = ContextCompat.getColor(applicationContext, R.color.colorTabSelected)
            tabLayout_fav.getTabAt(0)!!.icon!!.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
        tabLayout_fav.setOnTabSelectedListener(tab)

    }
}
