package net.uniquecomputer.lockqr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.uniquecomputer.lockqr.databinding.ActivityQrcodeBinding

class QRCode : AppCompatActivity() {
    private lateinit var binding: ActivityQrcodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}