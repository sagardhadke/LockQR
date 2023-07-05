package net.uniquecomputer.lockqr

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import net.uniquecomputer.lockqr.databinding.ActivityWebBinding
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.io.File
import java.io.FileOutputStream


class Web : AppCompatActivity() {
    lateinit var binding: ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val protocol = resources.getStringArray(R.array.protocol)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown, protocol)
        binding.autoprotocol.setAdapter(arrayAdapter)


        binding.generateWeb.setOnClickListener {

            val web = binding.webEt.text.toString()
            binding.webEt.setText(web)
            binding.webEt.clearFocus()

            if (web.isEmpty()) {
                binding.webEt.error = "Please Enter Website Url"
                binding.webEt.requestFocus()
                checkprotocol()
            } else {
                val bitmap = generateQrCode(web)
                binding.webQrCode.setImageBitmap(bitmap)
                binding.webText.text = "Congratulations! \n You've Created a QR Code!"
                binding.shareWeb.isVisible = true
                binding.downloadWeb.isVisible = true

                binding.downloadWeb.setOnClickListener {

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
                            val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            requestPermissions(permission, 1000)
                        } else {
                            saveQrCode()
                        }
                    } else {
                        saveQrCode()
                    }
                }
            }

        }

        binding.shareWeb.setOnClickListener {
            val bitmap = binding.webQrCode.drawable.toBitmap()
            val uri = getImageUri(bitmap)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/**"
            intent.putExtra(Intent.EXTRA_TEXT,"Download This Amazing App QR Code Generator App")
            intent.putExtra(Intent.EXTRA_STREAM,uri)
            startActivity(Intent.createChooser(intent,"Share QR Code"))
        }

    }

    private fun checkprotocol() {

        val protocol = binding.autoprotocol.text.toString()
        if (protocol.isEmpty()) {
            binding.autoprotocol.error = "Please Select a Protocol"
            MotionToast.createToast(this,
                "Failed ☹️",
                "Please Select a Valid Option!",
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this,R.font.poppins_semibold))
            binding.autoprotocol.requestFocus()
        } else {

            binding.webEt.setText(protocol)
            binding.autoprotocol.clearFocus()
            generateQrCode(protocol)
        }


    }



    private fun generateQrCode(web: String): Bitmap? {

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(web,BarcodeFormat.QR_CODE,512,512)
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

    private fun getImageUri(bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(contentResolver,bitmap,"QR Code Generated",null)
        return Uri.parse(path)
    }

    private fun saveQrCode() {
        val bitmap = binding.webQrCode.drawable.toBitmap()
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

}