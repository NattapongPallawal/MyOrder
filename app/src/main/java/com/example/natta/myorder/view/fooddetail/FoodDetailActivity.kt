package com.example.natta.myorder.view.fooddetail

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.viewmodel.FoodDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_food_detail.*

@Suppress("DEPRECATION")
class FoodDetailActivity : AppCompatActivity() {
    private var model: FoodDetailViewModel? = null
    private var food = Food()
    private var key = ""
    private var resKey: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        food = intent.getParcelableExtra("food")
        key = intent.getStringExtra("foodKey")
        resKey = intent.getStringExtra("resKey")
        model = ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        model!!.setResFoodKey(resKey, key,food)
        initView()

        model!!.getFoodSize().observe(this, Observer {
            if (it != null) {
                addChip(foodSize, it)
            }
        })

        model!!.getFoodType().observe(this, Observer {
            if (it != null) {
                addChip(foodType, it)
            }
        })

        foodType.setOnCheckedChangeListener { group, checkedId ->
            try {
                model!!.setPositionFoodType(checkedId - 2)
            } catch (e: IllegalStateException) {
                Toast.makeText(applicationContext, "null", Toast.LENGTH_SHORT).show()
            }
        }
        foodSize.setOnCheckedChangeListener { group, checkedId ->
            try {
                model!!.setPositionFoodSize(checkedId / 100 - 1)

            } catch (e: IllegalStateException) {
                Toast.makeText(applicationContext, "null", Toast.LENGTH_SHORT).show()
            }
        }

        add_to_favorite_food.setOnClickListener {
            add_to_favorite_food.playAnimation()
        }
    }

    private fun initView() {
        Picasso.get().load(food.picture).into(pic_FD)
        foodName_FD.text = food.foodName
        rating_FD.rating = food.rate!!.toFloat()
        price_FD.text = food.price.toString()

        removeAmount_FD.setOnClickListener {
            amount_FD.text = model!!.removeAmount()
        }

        addAmount_FD.setOnClickListener {
            amount_FD.text = model!!.addAmount()
        }
        btn_confirm.setOnClickListener {
            try {
                model!!.addOrderFood(50.0)
                finish()
            }catch (e : IndexOutOfBoundsException){
                Toast.makeText(applicationContext,"${e.message}",Toast.LENGTH_LONG).show()
            }

        }

        btn_cancel.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("PrivateResource")
    private fun addChip(view: ChipGroup, data: ArrayList<Pair<String, String>>?) {
        view.removeAllViews()
        if (data != null) {
            for (i in 1..data.size) {
                Log.d("addChip", i.toString())
                val chip = Chip(view.context)
                when (view.id) {
                    R.id.foodType -> {
                        chip.id = i + 1
                        chip.chipText = data[i - 1].second
                    }
                    R.id.foodSize -> {
                        chip.id = i * 100 + 1
                        chip.chipText = data[i - 1].second
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
        if (data != null) {
            if (data.isNotEmpty()) {
                if (view.id == R.id.foodSize) {
                    val c = findViewById<Chip>(1 * 100 + 1)
                    c.isChecked = true
                } else {
                    val c = findViewById<Chip>(1 + 1)
                    c.isChecked = true
                }
            }
        }


    }
}
