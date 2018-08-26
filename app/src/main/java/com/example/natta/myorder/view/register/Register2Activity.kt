package com.example.natta.myorder.view.register

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.natta.myorder.R
import kotlinx.android.synthetic.main.activity_register2.*

class Register2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

        register2_button.setOnClickListener {
            val firstName = register2_firstName.text.toString()
            val lastName = register2_lastName.text.toString()
            val phoneNumber = register2_phoneNumber.text.toString()

            val reg = arrayListOf<String>(firstName, lastName, phoneNumber, email, password)
            val i = Intent()
            i.putExtra("reg", reg)
            setResult(1, i)
            finish()
        }
    }
}
