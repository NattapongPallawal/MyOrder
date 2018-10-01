package com.example.natta.myorder.view.home


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.natta.myorder.R
import com.example.natta.myorder.view.favorite.FavoriteActivity
import com.example.natta.myorder.view.orderdetail.OrderDetailActivity
import com.example.natta.myorder.view.randomfood.RandomFoodActivity
import com.example.natta.myorder.viewmodel.HomeViewModel
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {
    private lateinit var model: HomeViewModel
    private var latitude = 0.0
    private var longitude = 0.0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        latitude = arguments!!.getDouble("latitude")
        longitude = arguments!!.getDouble("longitude")
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val adapterNearby = NearbyResAdapter(latitude, longitude)
        val adapterPopular = PopularResAdapter(latitude, longitude)
        model = ViewModelProviders.of(this).get(HomeViewModel::class.java)


        initNearbyResView(adapterNearby, view)
        initPopularResView(adapterPopular, view)
        model.getNearbyRes().observe(this, Observer {
            if (it != null) {
                adapterNearby.setData(it)
            }
        })

        model.getPopularRes().observe(this, Observer {
            if (it != null) {
                adapterPopular.setData(it)
            }
        })


        view.random_home.setOnClickListener {
            startActivity(Intent(this.context, RandomFoodActivity::class.java))
        }
        view.cart_home.setOnClickListener {
            startActivity(Intent(this.context, FavoriteActivity::class.java))
        }
        view.recent_home.setOnClickListener {
            if (model.ready.value!!.first) {
                val key = model.getOrder().value!!.first
                val i = Intent(this.context, OrderDetailActivity::class.java)
                i.putExtra("orderKey", key)
                startActivity(i)
            }

        }

        model.getMenu().observe(this, Observer {
            if (it != null) {
                Glide.with(this.context!!)
                        .load(it.second.picture)
                        .apply(RequestOptions.bitmapTransform(ColorFilterTransformation(Color.argb(70, 0, 0, 0))))
                        .into(view.img_food_recent_home)
                Log.d("getLastMenu", it.second.picture)
                view.foodName_recent_home.text = it.second.foodName
            }
        })
        return view
    }

    private fun initNearbyResView(adapterNearby: NearbyResAdapter, view: View) {
        view.recycleView_resnearby_home.isNestedScrollingEnabled = false
        view.recycleView_resnearby_home.setHasFixedSize(false)
        view.recycleView_resnearby_home.adapter = adapterNearby
        view.recycleView_resnearby_home.layoutManager = LinearLayoutManager(this.context)
    }

    private fun initPopularResView(adapterPopular: PopularResAdapter, view: View) {
        view.recycleView_resTop_home.isNestedScrollingEnabled = false
        view.recycleView_resTop_home.setHasFixedSize(false)
        view.recycleView_resTop_home.adapter = adapterPopular
        view.recycleView_resTop_home.layoutManager = LinearLayoutManager(this.context)
    }


}
