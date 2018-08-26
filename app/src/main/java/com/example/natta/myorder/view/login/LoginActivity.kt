package com.example.natta.myorder.view.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
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

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private var mAuth = FirebaseAuth.getInstance()
    private var mAuthListener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser
        if (user != null) {
            successAnimation()
            Delay(success_ani.duration).execute()
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
                startAnimation()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (!it.isSuccessful) {
                        stopAnimation()
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
            val i = Intent(applicationContext, Register1Activity::class.java)
            startActivityForResult(i, 2)
        }

    }


    private fun successAnimation() {
        login_con.visibility = View.GONE
        loading_ani.visibility = View.GONE
        success_ani.visibility = View.VISIBLE
        success_ani.playAnimation()
    }

    private fun stopAnimation() {
        login_con.visibility = View.VISIBLE
        loading_ani.visibility = View.GONE
        loading_ani.pauseAnimation()
    }

    private fun startAnimation() {
        login_con.visibility = View.GONE
        loading_ani.visibility = View.VISIBLE
        loading_ani.playAnimation()
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
                startAnimation()
                mAuth.createUserWithEmailAndPassword(reg[3], reg[4]).addOnCompleteListener {
                    if (it.isSuccessful) {
                        mCustomer.child(mAuth.uid!!).setValue(Customer(reg[0], reg[1], reg[2], null, null, null, null))
                    } else {
                        stopAnimation()
                    }
                }.addOnFailureListener {
                    Toast.makeText(applicationContext, "Create User Unsuccessful ${mAuth.uid}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    internal inner class Delay(private var duration: Long) : AsyncTask<Void,Void,Void>(){
        override fun doInBackground(vararg p0: Void?): Void? {
            Thread.sleep(duration)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            nextActivity()
        }

    }

    private fun nextActivity() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}
