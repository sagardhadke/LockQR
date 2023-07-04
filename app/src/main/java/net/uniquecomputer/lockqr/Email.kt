package net.uniquecomputer.lockqr

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import net.uniquecomputer.lockqr.databinding.ActivityEmailBinding
import java.io.File
import java.io.FileOutputStream

class Email : AppCompatActivity() {
    lateinit var binding: ActivityEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.generateMail.setOnClickListener {

            var email = binding.emailEt.text.toString()
            var content = binding.contentEmail.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEt.error = "Please Enter Email"
                binding.emailEt.requestFocus()
            } else if (content.isEmpty()) {
                binding.contentEmail.error = "Please Enter Content"
                binding.contentEmail.requestFocus()
            } else {

                binding.emailEt.clearFocus()
                binding.emailEt.isCursorVisible = false

                val bitmap = generateQrCode(email, content)
                binding.qrcodeMail.setImageBitmap(bitmap)
                binding.mailText.text = "Congratulations! \n You've Created a Email QR Code!"
                binding.shareMail.isVisible = true
                binding.downloadMail.isVisible = true
            }

        }

        binding.shareMail.setOnClickListener {

            val bitmap = binding.qrcodeMail.drawable.toBitmap()
            val uri = getImageUri(bitmap)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/**"
            intent.putExtra(Intent.EXTRA_STREAM,uri)
            intent.putExtra(Intent.EXTRA_TEXT,"Download This Amazing App QR Code Generator App")
            startActivity(Intent.createChooser(intent,"Share QR Code"))

        }

        binding.downloadMail.setOnClickListener {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission,1000)
                }else{
                    saveQrCode()
                }
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
        val bitmap = binding.qrcodeMail.drawable.toBitmap()
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
            Toast.makeText(this,"QR Code Saved",Toast.LENGTH_LONG).show()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun generateQrCode(mail: String, content: String): Bitmap? {

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode("Email:- $mail\n Content:- $content", BarcodeFormat.QR_CODE,512,512)
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

}


//    private fun validator() {
//        val email = binding.emailEt.text.toString().trim()
//        val content = binding.contentEmail.text.toString().trim()
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()&&email.isEmpty()) {
//            binding.emailEt.error = "Please Enter Email"
//            binding.emailEt.requestFocus()
//        } else if (content.isEmpty()) {
//            binding.contentEmail.error = "Please Enter Content"
//            binding.contentEmail.requestFocus()
//        }
//
//    }
