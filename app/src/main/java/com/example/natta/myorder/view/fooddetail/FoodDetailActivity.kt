package com.example.natta.myorder.view.fooddetail

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.viewmodel.FoodDetailViewModel
import com.example.natta.myorder.viewmodel.FoodViewModel
import kotlinx.android.synthetic.main.activity_food_detail.*

@Suppress("DEPRECATION")
class FoodDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        val model = ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)

        addChip(foodSize)
        addChip(foodType)

        foodType.setOnCheckedChangeListener { group, checkedId ->
            try {
                val chip = this.findViewById<Chip>(checkedId)
                Toast.makeText(applicationContext, "${chip.chipText}", Toast.LENGTH_SHORT).show()
            } catch (e: IllegalStateException) {
                Toast.makeText(applicationContext, "null", Toast.LENGTH_SHORT).show()
            }
        }
        foodSize.setOnCheckedChangeListener { group, checkedId ->
            try {
                val chip = this.findViewById<Chip>(checkedId)
                Toast.makeText(applicationContext, "${chip.chipText}", Toast.LENGTH_SHORT).show()
            } catch (e: IllegalStateException) {
                Toast.makeText(applicationContext, "null", Toast.LENGTH_SHORT).show()
            }
        }

        removeAmount_FD.setOnClickListener {
            amount_FD.setText(model.removeAmount())
        }

        addAmount_FD.setOnClickListener {
            amount_FD.setText(model.addAmount())

        }
    }

    @SuppressLint("PrivateResource")
    private fun addChip(view: ChipGroup) {
        for (i in 1..10) {
            val chip = Chip(view.context)
            when (view.id) {
                R.id.foodType -> {
                    chip.id = i
                    chip.chipText = "Hello"
                }
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
            view.addView(chip)
        }
    }
}
