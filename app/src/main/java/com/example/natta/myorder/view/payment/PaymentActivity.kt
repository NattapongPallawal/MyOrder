package com.example.natta.myorder.view.payment

import android.annotation.SuppressLint
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Restaurant
import com.example.natta.myorder.view.MainActivity
import com.example.natta.myorder.viewmodel.PaymentViewModel
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.custom_dialog_payment.*
import kotlin.math.roundToInt

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PaymentActivity : AppCompatActivity() {
    private var model = PaymentViewModel()
    private var resKey = ""
    private var restaurant = Restaurant()
    private var selectPromtPay: Boolean = true
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        resKey = intent.getStringExtra("resKey")
        model = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        val adapter = PaymentAdapter(this)
        list_payment.adapter = adapter
        list_payment.layoutManager = LinearLayoutManager(this)
        list_payment.setHasFixedSize(true)
        list_payment.setItemViewCacheSize(20)

        model.getRestaurant(resKey).observe(this, Observer {
            if (it != null) {
                res_name_PM.text = it.restaurantName.toString()
                this.restaurant = it
            }
        })

        model.getMyOrder(resKey).observe(this, Observer {
            if (it != null) {
                if (it.isNotEmpty()) {
                    if (it.first().second.table == null) {
                        textView_table_PM.visibility = View.GONE
                        table_PM.visibility = View.GONE
                    } else {
                        textView_table_PM.visibility = View.VISIBLE
                        table_PM.visibility = View.VISIBLE
                    }
                }

                orderAmount_PM.text = it.size.toString() + " รายการ"
                total_PM.text = model.getTotal().toString() + " ฿"

                adapter.setData(it)
            }
        })

        btn_payment_PM.setOnClickListener { it ->
            val i = Intent(applicationContext, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            val text = restaurant.promtPayID
            val textP = "พร้อมเพย์ : $text"
            val resName = restaurant.restaurantName
            //startActivity(i)
            if (selectPromtPay) {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.custom_dialog_payment)
                dialog.setCancelable(true)
                dialog.setCanceledOnTouchOutside(true)
                dialog.window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

                dialog.btn_goMain_PM.setOnClickListener {
                    dialog.dismiss()
                    model.addOrder(selectPromtPay)
                    startActivity(i)
                }

                dialog.qrcode_PM.setOnLongClickListener {
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("promtpayID", text)
                    clipboard.primaryClip = clip
                    Toast.makeText(applicationContext, "คัดลอกลงคลิปบอร์ดเรียบร้อยแล้ว", Toast.LENGTH_LONG).show()
                    return@setOnLongClickListener false
                }
                try {
                    val barcodeEncoder = BarcodeEncoder()
                    val bitmap = barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 1000, 1000)

                    val resources = this.resources
                    val scale = resources.displayMetrics.density

                    val newBitmap = bitmap.copy(bitmap.config, true)

                    val canvas = Canvas(newBitmap)
                    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                    val font = Typeface.createFromAsset(assets, "font/prompt.ttf")
                    paint.color = Color.BLACK
                    paint.typeface = Typeface.create(font, Typeface.NORMAL)
                    paint.textSize = (18 * scale).roundToInt().toFloat()

                    val bounds = Rect()
                    paint.getTextBounds(textP, 0, textP.length, bounds)
                    var x: Float = ((newBitmap.width - bounds.width()) / 2).toFloat()
                    var y: Float = (newBitmap.height - 40).toFloat()
                    canvas.drawText(textP, x, y, paint)

                    paint.textSize = (24 * scale).roundToInt().toFloat()
                    paint.typeface = Typeface.create(font, Typeface.BOLD)
                    paint.getTextBounds("$resName", 0, resName!!.length, bounds)
                    x = ((newBitmap.width - bounds.width()) / 2).toFloat()
                    y = (bounds.height() + 16).toFloat()
                    canvas.drawText("$resName", x, y, paint)


                    dialog.qrcode_PM.setImageBitmap(newBitmap)
                    dialog.saveQRCode_PM.setOnClickListener {
                        val saveUri = MediaStore.Images.Media.insertImage(
                                contentResolver,
                                newBitmap,
                                "$resName$text",
                                "Restaurant Name : $$resName ,PromtPayID : $text"
                        )
                        Toast.makeText(applicationContext, "บันทึกสำเร็จ", Toast.LENGTH_LONG).show()
                    }
                    dialog.show()
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                }


            } else {
                model.addOrder(selectPromtPay)
                startActivity(i)
            }


        }

        promtPay_PM.setCardBackgroundColor(resources.getColor(R.color.colorCard))
        promtPay_PM.setOnClickListener {
            selectPromtPay = true
            promtPay_PM.setCardBackgroundColor(resources.getColor(R.color.colorCard))
            cash_PM.setCardBackgroundColor(Color.WHITE)
        }
        cash_PM.setOnClickListener {
            selectPromtPay = false
            promtPay_PM.setCardBackgroundColor(Color.WHITE)
            cash_PM.setCardBackgroundColor(resources.getColor(R.color.colorCard))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
