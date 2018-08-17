package com.example.natta.myorder

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register2.*

class Register2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)
        var email = intent.getStringExtra("email")
        var password = intent.getStringExtra("password")

        Snackbar.make(register2, "$email  $password", Snackbar.LENGTH_LONG).show()

        register2_button.setOnClickListener {
            var firstName = register2_firstName.text.toString()
            var lastName = register2_lastName.text.toString()
            var phoneNumber = register2_phoneNumber.text.toString()

            var reg = arrayListOf<String>(firstName, lastName, phoneNumber, email, password)
            var i = Intent()
            i.putExtra("reg", reg)
            setResult(1, i)
            finish()


        }
    }
}
