package net.uniquecomputer.lockqr.Utils

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class Helper {
    companion object {
        fun generateQrCodeSms(recipient: String, message: String): Bitmap? {

            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(
                "Recipient:- $recipient \n Message:- $message",
                BarcodeFormat.QR_CODE,
                512,
                512
            )
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            return bitmap
        }

        fun generateQrCodemail(mail: String, content: String): Bitmap? {

            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(
                "Email:- $mail \n Content:- $content",
                BarcodeFormat.QR_CODE,
                512,
                512
            )
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            return bitmap
        }

        fun generateQrCodeWifi(networkName: String, pass: String): Bitmap? {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(
                "Network name:- $networkName\n Password:- $pass",
                BarcodeFormat.QR_CODE,
                512,
                512
            )
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            return bitmap
        }

        fun generateQrCode(text: String): Bitmap? {

            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            return bitmap
        }

    }
}