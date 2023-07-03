package net.uniquecomputer.lockqr

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.uniquecomputer.lockqr.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.textCard.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.websitecard.setOnClickListener {

            startActivity(Intent(this, Web::class.java))

        }

        binding.settings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

    }

}


