package com.example.natta.myorder.view.restaurantdetail

import android.annotation.SuppressLint
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Feedback
import com.example.natta.myorder.data.Restaurant
import com.example.natta.myorder.viewmodel.RestaurantDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_restaurant_detail.*
import kotlinx.android.synthetic.main.content_restaurant_detail.*
import kotlinx.android.synthetic.main.custom_dialog_feedback.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RestaurantDetailActivity : AppCompatActivity() {
    private var restaurant = Restaurant()
    private var resKey = ""
    private var distance = "0"
    private var model = RestaurantDetailViewModel()
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        model = ViewModelProviders.of(this).get(RestaurantDetailViewModel::class.java)

        restaurant = intent.getParcelableExtra("restaurant")
        distance = intent.getStringExtra("distance")
        resKey = intent.getStringExtra("resKey")

        model.setData(resKey,restaurant)

        val adapter = FeedbackAdapter()
        setDialog()
        loadImage()
        setTextValue()
        btn_review_RD.setOnClickListener {
            dialog.show()
        }
        recycleView_feedback.adapter = adapter
        recycleView_feedback.layoutManager = LinearLayoutManager(applicationContext)

        model.getFeedback(resKey).observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
            }
        })
        fabFavorite_RD.setOnClickListener {
            model.addFavoriteRes()
            Snackbar.make(res_detail, "เพิ่ม ${restaurant.restaurantName} \nลงในรายการโปรเรียบร้อยแล้ว", Snackbar.LENGTH_LONG).show()

        }


    }

    private fun setDialog() {
        var rating: Float = 0f
        var title = ""
        var review = ""
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog_feedback)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        dialog.resName_DI_RD.text = restaurant.restaurantName
        dialog.ratingBar_DI_RD.setOnRatingBarChangeListener { _, r, _ ->
                rating = r
        }
        dialog.title_RD.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                title = s.toString()
            }

        })
        dialog.review_FB_RD.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                review = s.toString()
            }

        })
        dialog.btn_send_FB_RD.setOnClickListener {
            model.setFeedback(rating,title,review,resKey)
            dialog.title_RD.text.clear()
            dialog.review_FB_RD.text.clear()
            dialog.ratingBar_DI_RD.rating = 0f
            dialog.dismiss()
        }
    }

    private fun loadImage() {
        Picasso.get().load(restaurant.picture).into(resImage_RD)
    }

    @SuppressLint("SetTextI18n")
    private fun setTextValue() {
        resName_RD.text = restaurant.restaurantName.toString()
        resLocation_RD.text = try {
            restaurant.address!!.address.toString()
        } catch (e: KotlinNullPointerException) {
            "ไม่พบ"
        }
        resOpen_RD.text = "เปิด ${restaurant.timeOpen} - ${restaurant.timeClose} น."
        resDistance_RD.text = "$distance  กม."
        resRating_RD.rating = restaurant.rating!!.toFloat()
        resCall_RD.text = restaurant.phoneNumber.toString()
        resAbout_RD.text = restaurant.about.toString()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}
