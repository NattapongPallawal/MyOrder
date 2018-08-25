package com.example.natta.myorder.view.favorite.restaurant

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.natta.myorder.R
import com.example.natta.myorder.viewmodel.FavoriteRestaurantViewModel
import kotlinx.android.synthetic.main.favorite_restaurant_fragment.view.*


class FavoriteRestaurantFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteRestaurantFragment()
    }

    private lateinit var viewModel: FavoriteRestaurantViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.favorite_restaurant_fragment, container, false)
        val adapter = FavoriteRestaurantAdapter(view.context)
        view.recyclerView_res_fav.adapter = adapter
        view.recyclerView_res_fav.layoutManager = LinearLayoutManager(view.context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoriteRestaurantViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
