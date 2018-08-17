package com.example.natta.myorder

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        button_fogot_next.setOnClickListener {
            val email = forgot_email.text.toString()
            val i = Intent(applicationContext,SelectVerificationActivity::class.java)
            i.putExtra("email",email)
            startActivity(i)
        }

    }
}
