package net.uniquecomputer.lockqr

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import net.uniquecomputer.lockqr.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.generator.setOnClickListener {

            val text = binding.EditText.text.toString().trim()
            binding.EditText.clearFocus()
            binding.EditText.isCursorVisible = false


            if (text.isEmpty()){
                binding.EditText.error = "Please Enter Any Text"
                binding.EditText.requestFocus()

            }else{

                val bitmap = generateQrCode(text)
                binding.qrcode.setImageBitmap(bitmap)
                binding.textView.text = "Congratulations! \n You've Created a QR Code!"
                binding.share.isVisible = true
                binding.download.isVisible = true

                binding.download.setOnClickListener {

                    //get user permission to save file
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

        }

        binding.share.setOnClickListener {

            //share qr code to other apps
            val bitmap = binding.qrcode.drawable.toBitmap()
            val uri = getImageUri(bitmap)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/**"
            intent.putExtra(Intent.EXTRA_TEXT,"Download This Amazing App QR Code Generator App")
            intent.putExtra(Intent.EXTRA_STREAM,uri)
            startActivity(Intent.createChooser(intent,"Share QR Code"))



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
            Toast.makeText(this,"QR Code Saved",Toast.LENGTH_LONG).show()
        }catch (e:Exception){
            e.printStackTrace()
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