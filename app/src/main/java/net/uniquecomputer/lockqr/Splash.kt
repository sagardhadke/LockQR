package net.uniquecomputer.lockqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import net.uniquecomputer.lockqr.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

       Handler(Looper.getMainLooper()).postDelayed({
           startActivity(Intent(this,Home::class.java))
           finish()
       },2500)


        //check dark_mode key value in shared preferences
        val sharedPref = getSharedPreferences("dark_mode", MODE_PRIVATE)
        val darkMode = sharedPref.getBoolean("dark_mode", false)
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }

}