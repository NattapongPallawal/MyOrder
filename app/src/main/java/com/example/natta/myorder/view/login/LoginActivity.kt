package com.example.natta.myorder.view.login

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.data.Address
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.view.MainActivity
import com.example.natta.myorder.view.forgotpassword.ForgotPasswordActivity
import com.example.natta.myorder.view.register.Register1Activity
import com.example.natta.myorder.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_login.*

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private var mAuth = FirebaseAuth.getInstance()
    private var mAuthListener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser
        if (user != null) {
            successAnimation(Manifest.permission.CAMERA)
            Delay(success_ani.duration).execute()
        }
    }
    private var mRootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var mCustomer = mRootRef.child("customer")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        premission(Manifest.permission.ACCESS_FINE_LOCATION)
//        premission(Manifest.permission.CAMERA)
        val model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        button_signin.setOnClickListener {
            val email = login_email.text.toString().trim()
            val password = login_password.text.toString().trim()
            if (model.checkEmail(email) && model.checkPassword(password)) {
                startAnimation()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (!it.isSuccessful) {
                        stopAnimation()
                        Snackbar.make(layout_login,"email หรือ รหัสผ่านผิด กรุณาลองใหม่อีกครั้ง",Snackbar.LENGTH_LONG).show()
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

    private fun premission(p: String) {
//        Dexter.withActivity(this)
//                .withPermission(Manifest.permission_group.CAMERA, Manifest.permission_group.LOCATION)
//                .withListener()

        Dexter.withActivity(this)
                .withPermission(p)
                .withListener(
                        object : PermissionListener {
                            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                                if (response != null) {
//                                    Toast.makeText(applicationContext, response.permissionName, Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                                token?.continuePermissionRequest()
                            }

                            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                                val dialogPermissionListener = DialogOnDeniedPermissionListener.Builder
                                        .withContext(this@LoginActivity)
                                        .withTitle("การเข้าถึงที่อยู่ปัจจุบัน")
                                        .withMessage("เราต้องการที่อยู่ปัจจุบันของคุณ")
                                        .withButtonText("Ok")
                                        .withIcon(R.mipmap.ic_launcher)
                                        .build()
                                dialogPermissionListener.onPermissionDenied(response)
//                                finish()

                            }
                        }
                )
                .check()
    }
    private fun successAnimation(camera: String) {
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
                        mCustomer.child(mAuth.uid!!).setValue(Customer(reg[0], reg[1], "", reg[2], "", "", Address()))
                    } else {
                        stopAnimation()
                    }
                }.addOnFailureListener {
                    Snackbar.make(layout_login,"email นี้ ถูกใช้งานไปแล้ว",Snackbar.LENGTH_LONG).show()
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
