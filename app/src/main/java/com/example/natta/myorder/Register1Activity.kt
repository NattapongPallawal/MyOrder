package com.example.natta.myorder

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register1.*

class Register1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)

        register1_next.setOnClickListener {
            val email = register1_email.text.toString().trim()
            val password = register1_password.text.toString().trim()
            var confirmPassword = register1_confirmPassword.text.toString()
            val i = Intent(applicationContext, Register2Activity::class.java)
            i.putExtra("email", email)
            i.putExtra("password", password)
            startActivityForResult(i, 1)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == 1) {
                setResult(2, data)
                finish()
            }

        }
    }
}
