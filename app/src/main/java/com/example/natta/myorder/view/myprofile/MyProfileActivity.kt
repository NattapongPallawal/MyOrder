package com.example.natta.myorder.view.myprofile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import com.example.natta.myorder.R
import kotlinx.android.synthetic.main.content_person.*
import kotlinx.android.synthetic.main.content_person_address.*

class MyProfileActivity : AppCompatActivity() {
    private var edtPerson = arrayListOf<EditText>()
    private var edtAddress = arrayListOf<EditText>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        binding()

        edit(btn_editPerson,edtPerson,btn_savePerson)
        save(btn_savePerson,edtPerson,btn_editPerson)

        edit(btn_editAddress,edtAddress,btn_saveAddress)
        save(btn_saveAddress,edtAddress,btn_editAddress)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    private fun save(btnSave: ImageButton, edtPerson: ArrayList<EditText>,btnEdit:ImageButton) {
        btnSave.setOnClickListener {
            for (i in edtPerson)
                i.isEnabled = false
            btnSave.visibility = View.GONE
            btnEdit.visibility = View.VISIBLE
        }
    }

    private fun edit(btnEdit: ImageButton, edtPerson: ArrayList<EditText>,btnSave:ImageButton) {
        btnEdit.setOnClickListener {
            for (i in edtPerson)
                i.isEnabled = true
            btnSave.visibility = View.VISIBLE
            btnEdit.visibility = View.GONE
        }
    }

    private fun binding() {
        edtPerson.add(edt_firstName as EditText)
        edtPerson.add(edt_lastName as EditText)
        edtPerson.add(edt_email as EditText)
        edtPerson.add(edt_personID as EditText)
        edtPerson.add(edt_phoneNum as EditText)
        edtPerson.add(edt_birthday as EditText)

        edtAddress.add(edt_address as EditText)
        edtAddress.add(edt_sub_district as EditText)
        edtAddress.add(edt_district as EditText)
        edtAddress.add(edt_province as EditText)
        edtAddress.add(edt_postCode as EditText)
    }


}
