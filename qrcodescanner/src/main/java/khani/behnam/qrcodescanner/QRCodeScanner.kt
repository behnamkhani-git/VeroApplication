package khani.behnam.qrcodescanner

import android.app.Activity
import android.content.Intent
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import khani.behnam.common.shared.QRCodeScanResultCallback

@Suppress("DEPRECATION")
class QRCodeScanner(
    private val activity: Activity,
    private val resultCallback: QRCodeScanResultCallback
) {

    fun initiateScan() {
        IntentIntegrator(activity).apply {
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            setPrompt("Scan a QR Code")
            setCameraId(0)
            setBeepEnabled(true)
            initiateScan()
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        resultCallback.onQRCodeScanResult(result.contents)
    }
}