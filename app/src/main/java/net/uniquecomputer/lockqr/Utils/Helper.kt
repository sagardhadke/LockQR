package net.uniquecomputer.lockqr.Utils

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class Helper {
    companion object {
        fun generateQrCodeSms(recipient: String, message: String, color: Int): Bitmap? {

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
                    if (color != null) {
                        bitmap.setPixel(x, y, if (bitMatrix[x, y]) color else Color.WHITE)
                    }else {
                        bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.parseColor("#CDB293"))
                    }
                }
            }
            return bitmap
        }

        fun generateQrCodemail(mail: String, content: String, getColor: Int, getBg: Int): Bitmap? {

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
                    if (getColor != null) {
                        bitmap.setPixel(x, y, if (bitMatrix[x, y]) getColor else Color.WHITE)
                    } else {
                        bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
            }
            return bitmap
        }

        fun generateQrCodeWifi(networkName: String, pass: String, color: Int): Bitmap? {
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

                    if (color != null) {
                        bitmap.setPixel(x, y, if (bitMatrix[x, y]) color else Color.WHITE)
                    } else {
                        bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
            }
            return bitmap
        }

        fun generateQrCode(text: String,color: Int,getBg: Int): Bitmap {

            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    when {
                        color != null && getBg != null -> {
                            bitmap.setPixel(x, y, if (bitMatrix[x, y]) color else getBg)
                        }
                        color != null -> {
                            bitmap.setPixel(x, y, if (bitMatrix[x, y]) color else Color.WHITE)
                        }
                        getBg != null -> {
                            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.WHITE else getBg)
                        }
                        else -> {
                            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }


                }
        }
            return bitmap
        }

        fun generateQrCodeContact(
            name: String,
            phone: String,
            email: String,
            company: String,
            jobTitle: String,
            address: String,
            color: Int
        ): Bitmap? {

                val writer = QRCodeWriter()
                val bitMatrix = writer.encode(
                    "Name:- $name \n Phone:- $phone \n Email:- $email \n Company:- $company \n Job Title:- $jobTitle \n Address:- $address",
                    BarcodeFormat.QR_CODE,
                    512,
                    512
                )
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        if (color != null) {
                            bitmap.setPixel(x, y, if (bitMatrix[x, y]) color else Color.WHITE)
                        } else {
                            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }
                }
                return bitmap
        }

        fun generateQrCodeCalendar(event: String, location: String, address: String, color: Int): Bitmap? {

                val writer = QRCodeWriter()
                val bitMatrix = writer.encode(
                    "Event:- $event \n Location:- $location \n Address:- $address",
                    BarcodeFormat.QR_CODE,
                    512,
                    512
                )
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        if (color != null) {
                            bitmap.setPixel(x, y, if (bitMatrix[x, y]) color else Color.WHITE)
                        } else {
                            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }
                }
                return bitmap
        }


    }
}