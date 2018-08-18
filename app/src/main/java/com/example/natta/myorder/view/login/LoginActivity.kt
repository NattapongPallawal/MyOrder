package com.example.natta.myorder.view.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.view.MainActivity
import com.example.natta.myorder.view.forgotpassword.ForgotPasswordActivity
import com.example.natta.myorder.view.register.Register1Activity
import com.example.natta.myorder.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var mAuth = FirebaseAuth.getInstance()
    private var mAuthListener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser
        if (user != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        } else {
            Snackbar.make(layout_login, "Sign Out", Snackbar.LENGTH_LONG).show()
        }
    }
    private var mRootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var mCustomer = mRootRef.child("customer")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val model = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        button_signin.setOnClickListener {
            val email = login_email.text.toString().trim()
            val password = login_password.text.toString().trim()
            if (model.checkEmail(email) && model.checkPassword(password)) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Snackbar.make(layout_login, "Sign In Unsuccessful", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
            if (!model.checkPassword(password)) {
                login_password.setError("รหัสต้องมากกว่า 6 ตัวอักษร", resources.getDrawable(R.drawable.ic_notifications))
            }
            if (!model.checkEmail(email)) {
                login_email.setError("อีเมลล์ไม่ถูกต้อง", resources.getDrawable(R.drawable.ic_notifications))
            }
        }
        login_forgot.setOnClickListener {
            startActivity(Intent(applicationContext, ForgotPasswordActivity::class.java))
        }
        register_login.setOnClickListener {
            var i = Intent(applicationContext, Register1Activity::class.java)
            startActivityForResult(i, 2)
        }

    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (resultCode == 2) {
                val reg = data!!.getStringArrayListExtra("reg")
                Toast.makeText(applicationContext, "${reg[0]} ${reg[1]} ${reg[2]} ${reg[3]} ${reg[4]}", Toast.LENGTH_SHORT).show()
                mAuth.createUserWithEmailAndPassword(reg[3], reg[4]).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(applicationContext, "Create User successful ${mAuth.uid}", Toast.LENGTH_LONG).show()
                        mCustomer.child(mAuth.uid!!).setValue(Customer(reg[0], reg[1], reg[2],null,null,null,null))
                    }
                    else{
                        Toast.makeText(applicationContext, "Create User Unsuccessful ${mAuth.uid}", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(applicationContext, "Create User Unsuccessful ${mAuth.uid}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
