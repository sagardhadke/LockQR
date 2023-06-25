package net.uniquecomputer.lockqr

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.encoder.QRCode
import net.uniquecomputer.lockqr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.generator.setOnClickListener {

            binding.shareqr.isEnabled = false

            val text = binding.EditText.text.toString().trim()

            if (text.isEmpty()){
                binding.EditText.error = "Please Enter Any Text"
                Toast.makeText(this,"Please Enter Your Name",Toast.LENGTH_LONG).show()
                binding.EditText.requestFocus()
            }else{

                val bitmap = generateQrCode(text)
                binding.qrcode.setImageBitmap(bitmap)
                binding.shareqr.isEnabled = true

            }

        }

    }

    private fun generateQrCode(text: String): Bitmap? {

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(text,BarcodeFormat.QR_CODE,512,512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
        for (x in 0 until width)
        {
            for (y in 0 until height)
            {
                bitmap.setPixel(x,y,if (bitMatrix[x,y])Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }
}