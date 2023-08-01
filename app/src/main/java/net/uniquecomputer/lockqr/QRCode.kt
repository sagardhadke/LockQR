package net.uniquecomputer.lockqr

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import net.uniquecomputer.lockqr.databinding.ActivityQrcodeBinding
import java.io.File
import java.io.FileOutputStream


class QRCode : AppCompatActivity() {
    private lateinit var binding: ActivityQrcodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkposition()
    }

    private fun checkposition() {

        when (intent.getIntExtra("position", 0)) {
            0 -> {
                binding.name.isVisible = true
                binding.name.hint = "Text"
                binding.generateText.text = "Generate QR Code for Text"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Your Text"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = generateQrCode(text)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        downloadQr()
                        shareQr()
                    }

                }
            }
            1 -> {

                val protocol = resources.getStringArray(R.array.protocol)
                val arrayAdapter = ArrayAdapter(this, R.layout.dropdown, protocol)
                binding.autoprotocol.setAdapter(arrayAdapter)
                binding.name.isVisible = true
                binding.protocol.isVisible = true
                binding.note.isVisible = true
                binding.name.hint = "Website"
                binding.generateText.text = "Generate QR Code for Website"
                binding.nameEt.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT
                binding.name.clearFocus()

                binding.name.setOnClickListener {
                    binding.nameEt.hint = "google.com"
                }

                binding.generator.setOnClickListener {
                    val web = binding.nameEt.text.toString().trim()
                    val protocol = binding.autoprotocol.text.toString()
                    val www = "://www."
                    val text = protocol + www + web
                    checkprotocol()
                    if (!Patterns.WEB_URL.matcher(web).matches()) {
                        binding.name.error = "Please Enter Valid Website Url"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = generateQrCode(text)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        downloadQr()
                        shareQr()
                    }
                }
            }
            2 -> {
                binding.name.isVisible = true
                binding.address.isVisible = true
                binding.name.hint = "E-mail"
                binding.address.hint = "Content"
                binding.generateText.text = "Generate QR Code for Email"
                binding.nameEt.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    val content = binding.addressEt.text.toString().trim()

                    if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                        binding.name.error = "Please Enter Valid Email"
                        binding.name.requestFocus()
                    }else if (content.isEmpty()) {
                        binding.address.error = "Please Enter Content"
                        binding.address.requestFocus()
                    }
                    else {
                        binding.name.error = null
                        binding.address.error = null
                        binding.name.clearFocus()
                        binding.address.clearFocus()
                        val bitmap = generateQrCodemail(text, content)
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        downloadQr()
                        shareQr()
                    }

                }
            }
            3 -> {
                binding.name.isVisible = true
                binding.address.isVisible = true
                binding.name.hint = "Recipient"
                binding.address.hint = "Message"
                binding.generateText.text = "Generate QR Code for Sms"
                binding.nameEt.inputType = InputType.TYPE_CLASS_NUMBER
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val recipient = binding.nameEt.text.toString().trim()
                    val message = binding.addressEt.text.toString().trim()

                    if (!Patterns.PHONE.matcher(recipient).matches()) {
                        binding.name.error = "Please Enter Valid Number"
                        binding.name.requestFocus()
                    }else if (message.isEmpty()) {
                        binding.address.error = "Please Enter Message"
                        binding.address.requestFocus()
                    }
                    else {
                        binding.name.error = null
                        binding.address.error = null
                        binding.name.clearFocus()
                        binding.address.clearFocus()
                        val bitmap = generateQrCodeSms(recipient, message)
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        downloadQr()
                        shareQr()
                    }

                }
            }
            4 ->{
                binding.name.isVisible = true
                binding.email.isVisible = true
                binding.name.hint = "Network Name (SSID)"
                binding.email.hint = "Password"
                binding.generateText.text = "Generate QR Code for Wifi"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val network_name = binding.nameEt.text.toString().trim()
                    val pass = binding.emailEt.text.toString().trim()

                    if (network_name.isEmpty()) {
                        binding.name.error = "Please Enter Network Name"
                        binding.name.requestFocus()
                    }else if (pass.isEmpty()) {
                        binding.email.error = "Please Enter Your Wifi Password"
                        binding.email.requestFocus()
                    }
                    else {
                        binding.name.error = null
                        binding.address.error = null
                        binding.name.clearFocus()
                        binding.address.clearFocus()
                        val bitmap = generateQrCodeWifi(network_name, pass)
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        downloadQr()
                        shareQr()
                    }

                }
            }
            5 ->{
                binding.name.isVisible = true
                binding.name.hint = "Phone Number"
                binding.generateText.text = "Generate QR Code for Phone Number"
                binding.nameEt.inputType = InputType.TYPE_CLASS_NUMBER
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()

                    if (!Patterns.PHONE.matcher(text).matches()) {
                        binding.name.error = "Please Enter Valid Number"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = generateQrCode(text)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        downloadQr()
                        shareQr()
                    }

                }
            }

        }

        when(val title = intent.getStringExtra("title")) {
            "Youtube" -> {
                binding.name.isVisible = false
                Toast.makeText(this, "${title}", Toast.LENGTH_SHORT).show()
            }
            "Whatsapp" -> {
                binding.name.hint = "Whatsapp"
                binding.nameEt.inputType = InputType.TYPE_CLASS_NUMBER
                binding.name.isVisible = true
                Toast.makeText(this, "${title}", Toast.LENGTH_SHORT).show()
            }
            "Facebook" -> {
                binding.download.isVisible = true
                Toast.makeText(this, "${title}", Toast.LENGTH_SHORT).show()
            }
            "Twitter" -> {
                Toast.makeText(this, "${title}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun generateQrCodeWifi(networkName: String, pass: String): Bitmap? {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode("Network name:- $networkName\n Password:- $pass", BarcodeFormat.QR_CODE,512,512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
        for (x in 0 until width)
        {
            for (y in 0 until height)
            {
                bitmap.setPixel(x,y,if (bitMatrix[x,y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    private fun checkprotocol() {
        val protocol = binding.autoprotocol.text.toString()
        if (protocol.isEmpty()) {
            binding.autoprotocol.error = "Please Select a Protocol"
            binding.autoprotocol.requestFocus()
            Toast.makeText(this, "Please Select a Protocol", Toast.LENGTH_SHORT).show()
        } else {
            binding.autoprotocol.error = null
            generateQrCode(protocol)
        }

    }


    private fun shareQr() {
        binding.share.setOnClickListener {

            val bitmap = binding.qrcode.drawable.toBitmap()
            val uri = getImageUri(bitmap)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/**"
            intent.putExtra(Intent.EXTRA_TEXT,"I am Generating This Amazing QR Code using Pocket QR App \n 1️⃣ 100% FREE & No Ads! \n 2️⃣ Easily create your own unlimited non-expiring QR codes in a matter of seconds \n 3️⃣ Custom Any Text \n 4️⃣  Download QR Code \n 5️⃣   Share QR Code to Any Social Media Platform \n Made in India app. Click the link to download now: https://rb.gy/du9t9")
            intent.putExtra(Intent.EXTRA_STREAM,uri)
            startActivity(Intent.createChooser(intent,"Share QR Code"))

        }
    }

    private fun downloadQr() {
        binding.download.setOnClickListener {

            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED){
                val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission,1000)
            }else{
                saveQrCode()
            }

        }
    }


    private fun getImageUri(bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(contentResolver,bitmap,"QR Code Generated",null)
        return Uri.parse(path)
    }

    private fun saveQrCode() {
        val bitmap = binding.qrcode.drawable.toBitmap()
        val filename = "${System.currentTimeMillis()}.jpg"
        val externalCacheDir = getExternalFilesDir(null)

        MediaStore.Images.Media.insertImage(contentResolver,bitmap,filename,"QR Code Generated")

        var path = externalCacheDir
        val file = File(path,filename)
        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out)
            out.flush()
            out.close()
            Toast.makeText(this,"QR Code Saved", Toast.LENGTH_LONG).show()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    private fun generateQrCodemail(mail: String, content: String): Bitmap? {

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode("Email:- $mail \n Content:- $content", BarcodeFormat.QR_CODE,512,512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
        for (x in 0 until width)
        {
            for (y in 0 until height)
            {
                bitmap.setPixel(x,y,if (bitMatrix[x,y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    private fun generateQrCodeSms(recipient: String, message: String): Bitmap? {

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode("Recipient:- $recipient \n Message:- $message", BarcodeFormat.QR_CODE,512,512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
        for (x in 0 until width)
        {
            for (y in 0 until height)
            {
                bitmap.setPixel(x,y,if (bitMatrix[x,y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    private fun generateQrCode(text: String): Bitmap? {

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE,512,512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565)
        for (x in 0 until width)
        {
            for (y in 0 until height)
            {
//                bitmap.setPixel(x,y,if (bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                bitmap.setPixel(x,y,if (bitMatrix[x,y]) ContextCompat.getColor(this,R.color.qr_code) else Color.WHITE)
            }
        }
        return bitmap
    }
}