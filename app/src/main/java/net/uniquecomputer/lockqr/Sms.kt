package net.uniquecomputer.lockqr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.uniquecomputer.lockqr.databinding.ActivitySmsBinding

class Sms : AppCompatActivity() {
    lateinit var binding : ActivitySmsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}