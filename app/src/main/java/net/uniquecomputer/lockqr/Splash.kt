package net.uniquecomputer.lockqr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.uniquecomputer.lockqr.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}