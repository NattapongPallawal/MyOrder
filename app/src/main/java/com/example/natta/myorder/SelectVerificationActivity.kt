package com.example.natta.myorder

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_select_verification.*
import java.util.concurrent.TimeUnit

class SelectVerificationActivity : AppCompatActivity() {
    var email: String = ""
    private var mAuth = FirebaseAuth.getInstance()
    private var mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential?) {
            Toast.makeText(applicationContext, "Completed", Toast.LENGTH_LONG).show()

        }

        override fun onVerificationFailed(p0: FirebaseException?) {
            Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()

        }

        override fun onCodeSent(p0: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(p0, p1)
            Toast.makeText(applicationContext, "Send", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_verification)
        email = intent.getStringExtra("email")
        Snackbar.make(selectVerificationActivity, email, Snackbar.LENGTH_LONG).show()
        select_verification_email.text = "ส่ง link รีเซ็ตรหัสผ่าน ไปทางอีเมลล์ \n$email"
        select_verification_phoneNumber.text = "ส่งรหัสยืนยันไปที่หมายเลข \n0801234567"

        select_email.setOnClickListener {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext, "ระบบได้ส่งอีเมลล์รีเซ็ตรหัสผ่านไปที่ $email เรียบร้อยแล้ว", Toast.LENGTH_LONG).show()
                }
            }
        }
        select_phoneNumber.setOnClickListener {
            PhoneAuthProvider.getInstance().verifyPhoneNumber("+66898487384", 60, TimeUnit.SECONDS, this, mCallback)

        }
    }
}
