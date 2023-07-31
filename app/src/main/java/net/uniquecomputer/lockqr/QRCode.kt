package net.uniquecomputer.lockqr

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
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

        when (val position = intent.getIntExtra("position", 0)) {
            0 -> {
                binding.name.isVisible = true
                binding.name.hint = "Text"
                binding.generateText.text = "Generate QR Code for Text"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    binding.name.clearFocus()

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Any Text"
                        binding.name.requestFocus()
                    } else {
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
                binding.name.hint = "Address"
                binding.nameEt.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD

                binding.name.isVisible = true
                Toast.makeText(this, "${position}", Toast.LENGTH_SHORT).show()
            }
            2 -> {
                binding.download.isVisible = true
                Toast.makeText(this, "${position}", Toast.LENGTH_SHORT).show()
            }
            8 -> {
                Toast.makeText(this, "$position", Toast.LENGTH_SHORT).show()
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