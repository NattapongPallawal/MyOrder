package com.example.natta.myorder.view.favorite.food

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.natta.myorder.R
import com.example.natta.myorder.viewmodel.FavoriteFoodViewModel
import kotlinx.android.synthetic.main.favorite_food_fragment.view.*


class FavoriteFoodFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteFoodFragment()
    }

    private lateinit var viewModel: FavoriteFoodViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.favorite_food_fragment, container, false)
        val adapter = FavoriteFoodAdapter(view.context)
        view.recyclerView_fav_food.adapter = adapter
        view.recyclerView_fav_food.layoutManager = LinearLayoutManager(view.context)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoriteFoodViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
