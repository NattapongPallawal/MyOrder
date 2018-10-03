package com.example.natta.myorder.view.scanqrcode

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.view.food.FoodActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_scan_qrcode.*

class ScanQRCodeActivity : AppCompatActivity(), ScanQRFragment.ScanQRCodeResultListener {
    private var mNfcAdapter: NfcAdapter? = null
    private val MIME_TEXT_PLAIN = "text/plain"
    private val TAG = "CheckNFC"
    private var editor: SharedPreferences.Editor? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qrcode)
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
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
                                        .withContext(this@ScanQRCodeActivity)
                                        .withTitle("การเข้าถึงกล้องถ่ายรูป")
                                        .withMessage("ต้องการใช้งานกล้องเพื่อนสแกน QR CODE")
                                        .withButtonText("Ok")
                                        .withIcon(R.mipmap.ic_launcher)
                                        .build()
                                dialogPermissionListener.onPermissionDenied(response)
//                                finish()

                            }
                        }
                ).onSameThread()
                .check()
        editor = this.getSharedPreferences("MY_ORDER", Context.MODE_PRIVATE).edit()

        Toast.makeText(applicationContext,"กดที่กล้องเพื่อ เปิด/ปิด แฟลช",Toast.LENGTH_LONG).show()
        showFragment()



        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (mNfcAdapter == null) {
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show()
            return

        } else if (!mNfcAdapter!!.isEnabled) {
            Toast.makeText(this, "NFC is disabled.", Toast.LENGTH_LONG).show()
        }
//        NFCIntent(intent)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            receiveMessageFromDevice(intent)
        }
    }


    private fun showFragment() {
        val fragment = ScanQRFragment()
        fragment.setOnScanQRCodeResultListener(this)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.qr_code_frame, fragment)
        fragmentTransaction.commit()
    }

    override fun onResult(result: String) {
        val r = result.split(",")
        Toast.makeText(applicationContext, "${r.first()} ${r.last()}", Toast.LENGTH_LONG).show()
        val i = Intent(this, FoodActivity::class.java)
        i.putExtra("resKey", r.first())
        editor!!.putString("TABLE", r.last())
        editor!!.commit()

        startActivity(i)
    }

    override fun onResume() {
        super.onResume()
        enableForegroundDispatch(this, this.mNfcAdapter)
        receiveMessageFromDevice(intent)
    }

    private fun receiveMessageFromDevice(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            with(parcelables) {
                val inNdefMessage = this[0] as NdefMessage
                val inNdefRecords = inNdefMessage.records
                val ndefRecord_0 = inNdefRecords[0]

                val inMessage = String(ndefRecord_0.payload)
                nfc_SQ.text = inMessage
            }
        }
    }

    private fun enableForegroundDispatch(activity: AppCompatActivity, adapter: NfcAdapter?) {

        val intent = Intent(activity.applicationContext, activity.javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(activity.applicationContext, 0, intent, 0)

        val filters = arrayOfNulls<IntentFilter>(1)
        val techList = arrayOf<Array<String>>()

        filters[0] = IntentFilter()
        with(filters[0]) {
            this?.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
            this?.addCategory(Intent.CATEGORY_DEFAULT)
            try {
                this?.addDataType(MIME_TEXT_PLAIN)
            } catch (ex: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("Check your MIME type")
            }
        }

        adapter?.enableForegroundDispatch(activity, pendingIntent, filters, techList)
    }

    private fun disableForegroundDispatch(activity: AppCompatActivity, adapter: NfcAdapter?) {
        adapter?.disableForegroundDispatch(activity)
    }

    override fun onPause() {
        super.onPause()
        disableForegroundDispatch(this, this.mNfcAdapter)
    }
}


