package com.example.natta.myorder.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.natta.myorder.data.Customer
import com.example.natta.myorder.data.Feedback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RestaurantDetailViewModel : ViewModel() {
    private var mRootRef = FirebaseDatabase.getInstance().reference
    private var mAuth = FirebaseAuth.getInstance()
    private var mFeedback = MutableLiveData<ArrayList<Feedback>>()
    private var mOneCustomer = MutableLiveData<Customer>()


    fun getFeedback(resID: String): MutableLiveData<ArrayList<Feedback>> {

        val mFeedbackRef = mRootRef.child("feedback").child(resID) // resID
        mFeedbackRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val feedbackList = arrayListOf<Feedback>()

                p0.children.forEach {
                    feedbackList.add(it.getValue(Feedback::class.java)!!)
                }
                mFeedback.value = feedbackList
            }

        })
        return mFeedback

    }

    fun setFeedback(rating: Float, title: String, review: String, resKey: String) {
        val feedback = Feedback(mAuth.currentUser!!.uid,null,title,review,rating.toDouble(),ServerValue.TIMESTAMP)
        val mFeedbackRef = mRootRef.child("feedback").child(resKey)
                mFeedbackRef.push().setValue(feedback)
    }
}
