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
import android.view.View
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.data.Select
import com.example.natta.myorder.viewmodel.FoodDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_food_detail.*

@Suppress("DEPRECATION")
class FoodDetailActivity : AppCompatActivity() {
    private var model: FoodDetailViewModel? = null
    private var food = Food()
    private var key = ""
    private var selectKey = ""
    private var resKey: String = ""
    private var select = Select()
    private var formFood: Boolean = true
    private var chipIDType: Int = 0
    private var chipIDSize: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        model = ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        key = intent.getStringExtra("foodKey")
        resKey = intent.getStringExtra("resKey")
        try {
            food = intent.getParcelableExtra("food")
            model!!.setResFoodKey(resKey, key, food)
            formFood = true
        } catch (e: IllegalStateException) {
            select = intent.getParcelableExtra("select")
            selectKey = intent.getStringExtra("selectKey")
            model!!.setResFoodKey(resKey, key, null, select.amount!!)
            formFood = false

        }

        model!!.getReady().observe(this, Observer {
            if (it!!) {
                food = model!!.getFood()
                initView()
            }
        })

        model!!.getFoodSize().observe(this, Observer {
            if (it != null) {
                addChip(foodSize, it)
            }
        })

        model!!.getFoodType().observe(this, Observer {
            if (it != null) {
                addChip(foodType, it)
                if (it.isNotEmpty()) {
                    showFoodType()
                } else {
                    hideFoodType()
                }
            }
        })

        foodType.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                try {
                    var t = findViewById<Chip>(checkedId)
                    model!!.setPositionFoodType(checkedId - 2)
                    chipIDType = checkedId
                    Log.d("checkedId1", "$checkedId")
                } catch (e: IllegalStateException) {
                    findViewById<Chip>(chipIDType).isChecked = true
                }
            } else {
                findViewById<Chip>(chipIDType).isChecked = true
            }
        }
        foodSize.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                try {
                    var t = findViewById<Chip>(checkedId)
                    model!!.setPositionFoodSize(checkedId / 100 - 1)
                    chipIDSize = checkedId
                    Log.d("checkedId2", "$checkedId")
                } catch (e: IllegalStateException) {
                    findViewById<Chip>(chipIDSize).isChecked = true
                }
            } else {
                Log.d("checkedId3", "$chipIDSize")
                findViewById<Chip>(chipIDSize).isChecked = true
            }
        }

        add_to_favorite_food.setOnClickListener {
            add_to_favorite_food.playAnimation()
        }
    }

    private fun showFoodType() {
        textView_mat_FD.visibility = View.VISIBLE
        foodType.visibility = View.VISIBLE
        div_mat_FD.visibility = View.VISIBLE
    }

    private fun hideFoodType() {
        textView_mat_FD.visibility = View.GONE
        foodType.visibility = View.GONE
        div_mat_FD.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        Picasso.get().load(food.picture).into(pic_FD)
        foodName_FD.text = food.foodName
        rating_FD.rating = food.rate!!.toFloat()
        price_FD.text = food.price.toString() + " ฿"

        amount_FD.text = model!!.getAmount().toString()

        removeAmount_FD.setOnClickListener {
            amount_FD.text = model!!.removeAmount()
        }

        addAmount_FD.setOnClickListener {
            amount_FD.text = model!!.addAmount()
        }
        btn_confirm.setOnClickListener {
            try {
                model!!.addOrderFood(food.price!!, formFood, selectKey)
                finish()
            } catch (e: IndexOutOfBoundsException) {
                Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_LONG).show()
            }

        }

        btn_cancel.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("PrivateResource")
    private fun addChip(view: ChipGroup, data: ArrayList<Pair<String, String>>?) {
        view.removeAllViews()
        var change = true
        var chipID = 0
        if (data != null) {
            for (i in 1..data.size) {
                Log.d("addChip", i.toString())
                val chip = Chip(view.context)
                when (view.id) {
                    R.id.foodType -> {
                        chip.id = i + 1
                        chip.chipText = data[i - 1].second
                        if (data[i - 1].first == select.foodTypeID) {
                            chipID = i + 1
                            change = false
                        }
                        if (change) {
                            chipID = 2
                        }
                    }
                    R.id.foodSize -> {
                        chip.id = i * 100 + 1
                        chip.chipText = data[i - 1].second
                        if (data[i - 1].first == select.foodSizeID) {
                            chipID = i * 100 + 1
                            change = false
                        }
                        if (change) {
                            chipID = 1 * 100 + 1
                        }
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
                    val c = findViewById<Chip>(chipID)
                    c.isChecked = true
                } else {
                    val c = findViewById<Chip>(chipID)
                    c.isChecked = true
                }
            } else {

            }
        }
    }
}
