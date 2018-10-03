package com.example.natta.myorder.view.scanqrcode


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.natta.myorder.R
import com.example.natta.myorder.view.food.FoodActivity
import com.google.zxing.ResultPoint
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import kotlinx.android.synthetic.main.fragment_scan_qr.view.*

class ScanQRFragment : Fragment(),DecoratedBarcodeView.TorchListener {
    private var flash : Boolean = true
    override fun onTorchOn() {
        Toast.makeText(this.context,"เปิดแฟลชแล้ว",Toast.LENGTH_LONG).show()
    }

    override fun onTorchOff() {
        Toast.makeText(this.context,"ปิดแฟลชแล้ว",Toast.LENGTH_LONG).show()

    }

    private var barcodeView: DecoratedBarcodeView? = null
    private var r: String? = null
    private lateinit var listener: ScanQRCodeResultListener
    private var callback = object : BarcodeCallback {
        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {

        }

        override fun barcodeResult(result: BarcodeResult?) {

            if (result == null || result.text == r) {
                return
            }
            listener.onResult(result.text)
            r = ""
        }


    }

    fun setOnScanQRCodeResultListener(l: ScanQRCodeResultListener) {
        this.listener = l
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_scan_qr, container, false)
        barcodeView = view.decorated_barcode_view as DecoratedBarcodeView
        barcodeView!!.decodeContinuous(callback)
        barcodeView!!.setTorchListener(this)
//        barcodeView!!.setTorchOff()
        view.barcodeView_SQ.setOnClickListener {

            flash = if (flash){
                barcodeView!!.setTorchOn()
                !flash
            }else{
                barcodeView!!.setTorchOff()
                !flash
            }

        }
//        startActivity(Intent(activity, ShowFoodActivity::class.java))
        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        view.decorated_barcode_view.setTorchOff()
//    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (barcodeView != null) {
            if (isVisibleToUser) {
                barcodeView!!.resume()
            } else {
                barcodeView!!.pauseAndWait()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        barcodeView!!.pauseAndWait()
    }

    override fun onResume() {
        super.onResume()
        barcodeView!!.resume()
    }

    private fun scanFromFragment() {
        IntentIntegrator.forSupportFragment(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        Toast.makeText(this.context, result.contents, Toast.LENGTH_LONG).show()
    }

    interface ScanQRCodeResultListener {
        fun onResult(result: String)
    }

}
