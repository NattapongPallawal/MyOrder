package com.example.natta.myorder.view.forgotpassword

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.natta.myorder.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    private var mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        button_send_email.setOnClickListener {
            val email = forgot_email.text.toString()
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext, "ระบบได้ส่งอีเมลล์รีเซ็ตรหัสผ่านไปที่ $email เรียบร้อยแล้ว", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}
