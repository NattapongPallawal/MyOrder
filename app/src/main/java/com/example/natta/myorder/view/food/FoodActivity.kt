package com.example.natta.myorder.view.food

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.R.id.shopping_cart
import com.example.natta.myorder.data.Food
import com.example.natta.myorder.view.myorder.MyOrderActivity
import com.example.natta.myorder.viewmodel.FoodViewModel
import kotlinx.android.synthetic.main.activity_food.*

@Suppress("DEPRECATION")
class FoodActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var restaurant = ""
    var type = arrayListOf<Pair<String,String>>()
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


        val adapter = FoodAdapter(applicationContext)
        setUpFood(adapter)

        model.getFood(restaurant)?.observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
            }
        })
        model.getMenuType(restaurant).observe(this, Observer {
            type = it!!
            updateType()
        })
        spinner_food.onItemSelectedListener = this


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d("SelectType",type[p2].second)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_food,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when(item.itemId){
                R.id.shopping_cart ->{
                    startActivity(Intent(applicationContext,MyOrderActivity::class.java))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun updateType() {
        val typeV = arrayListOf<String>()
        this.type.forEach {
            typeV.add(it.second)
        }
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeV)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_food.adapter = arrayAdapter

//        spinner_food.setonitems

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

//    @SuppressLint("PrivateResource")
//    private fun addChip(type: ArrayList<String>) {
//        val view = menuType_menu as ChipGroup
//        view.removeAllViews()
//        view.isSingleSelection = true
//        type.forEach {
//            var i = 1
//            val chip = Chip(view.context)
//            chip.id = i
//            chip.chipText = it
//            chip.setPadding(5, 5, 5, 5)
//            chip.isClickable = true
//            chip.isCheckable = true
//            chip.gravity = Gravity.CENTER
//            chip.chipBackgroundColor = resources.getColorStateList(R.color.mtrl_chip_background_color)
//            chip.isFocusable = true
//            chip.isCheckedIconEnabled = true
//            view.addView(chip)
//
//            if (i == 1) view.check(chip.id)
//
//            ++i
//        }
//    }
}

