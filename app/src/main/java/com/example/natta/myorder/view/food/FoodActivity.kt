package com.example.natta.myorder.view.food

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.View
import com.example.natta.myorder.R
import com.example.natta.myorder.viewmodel.FoodViewModel
import kotlinx.android.synthetic.main.activity_food.*

@Suppress("DEPRECATION")
class FoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        addChip(foodSize)

        val adapter = FoodAdapter(applicationContext)
        setUpFood(adapter)
        val model = ViewModelProviders.of(this).get(FoodViewModel::class.java)
        model.getFood().observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setUpFood(adapter: FoodAdapter) {

        recyclerView_food.adapter = adapter
        recyclerView_food.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView_food.setHasFixedSize(true)
        recyclerView_food.setItemViewCacheSize(10)
        recyclerView_food.isDrawingCacheEnabled = true
        recyclerView_food.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
    }

    @SuppressLint("PrivateResource")
    private fun addChip(view: ChipGroup) {
        for (i in 1..10) {
            val chip = Chip(view.context)
            when (view.id) {
                R.id.foodSize -> {
                    chip.id = i * 100
                    chip.chipText = "ธรรมดา"
                }
            }
            chip.setPadding(5, 5, 5, 5)
            chip.isClickable = true
            chip.isCheckable = true
            chip.gravity = Gravity.CENTER
            chip.chipBackgroundColor = resources.getColorStateList(R.color.mtrl_chip_background_color)
            chip.isFocusable = true
            chip.isCheckedIconEnabled = false
            view.addView(chip)
        }
    }
}
