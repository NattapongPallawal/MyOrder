package com.example.natta.myorder

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class FireBaseOffline : Application (){
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

    }
}