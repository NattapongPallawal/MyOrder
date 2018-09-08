package com.example.natta.myorder.view.food

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.view.myorder.MyOrderActivity
import com.example.natta.myorder.viewmodel.FoodViewModel
import kotlinx.android.synthetic.main.activity_food.*

@Suppress("DEPRECATION")
class FoodActivity : AppCompatActivity() {
    private var restaurant = ""
    private var type = arrayListOf<String>()
    private var food = arrayListOf<Pair<String, Food>>()
    private var selectType = ""
    private lateinit var adapter: FoodAdapter
    private var chipID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        val model = ViewModelProviders.of(this).get(FoodViewModel::class.java)
        try {
            restaurant = intent.getStringExtra("resKey")

        } catch (e: IllegalStateException) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
        }

        adapter = FoodAdapter(applicationContext)
        setUpFood(adapter)

        model.getMenuType(restaurant).observe(this, Observer {
            if (it != null) {
                this.type = it
                addChip(it)
            }
        })

        menuType.setOnCheckedChangeListener { group, checkedId ->
            try {
                selectType = findViewById<Chip>(checkedId).chipText.toString()
                updateDataRecycleView()
                chipID = checkedId
            } catch (e: IllegalStateException) {
                findViewById<Chip>(chipID).isChecked = true
            }

        }

        model.getFood(restaurant)?.observe(this, Observer {
            if (it != null) {
                food = it
                updateDataRecycleView()
            }
        })
    }

    private fun updateDataRecycleView() {
        val selectFood = arrayListOf<Pair<String, Food>>()
        food.forEach {
            if (selectType == it.second.type) {
                selectFood.add(it)
            }
        }
        adapter.setData(selectFood, restaurant)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_food, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.shopping_cart -> {
                    val i = Intent(applicationContext, MyOrderActivity::class.java)
                    i.putExtra("resKey", restaurant)
                    startActivity(i)
                }
            }
        }
        return super.onOptionsItemSelected(item)
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
    private fun addChip(type: ArrayList<String>) {
        val view = menuType as ChipGroup
        view.removeAllViews()
        for (i in 1..type.size) {
            val chip = Chip(view.context)
            chip.id = i + 1
            chip.chipText = type[i - 1]
            chip.setPadding(5, 5, 5, 5)
            chip.isClickable = true
            chip.isCheckable = true
            chip.gravity = Gravity.CENTER
            chip.chipBackgroundColor = resources.getColorStateList(R.color.mtrl_chip_background_color)
            chip.isFocusable = true
            chip.isCheckedIconEnabled = true
            view.addView(chip)
        }
        if (type.isNotEmpty()) {
            val l = findViewById<Chip>(1 + 1)
            l.isChecked = true
        }

    }
}

