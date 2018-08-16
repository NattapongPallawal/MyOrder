package com.example.natta.myorder

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.database.*

class OrderHistoryActivity : AppCompatActivity() {
    var mRootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    var username = mRootRef.child("customer").child("cus-test1").child("firstName")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)
        mRootRef.keepSynced(true)
        username.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, p0.message, Toast.LENGTH_SHORT).show()

            }

            override fun onDataChange(p0: DataSnapshot) {
                var firstname = p0.value as String

                Toast.makeText(applicationContext, firstname, Toast.LENGTH_SHORT).show()

            }

        })

    }
}
