package net.uniquecomputer.lockqr

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import net.uniquecomputer.lockqr.Utils.Constants
import net.uniquecomputer.lockqr.databinding.ActivityMainBinding
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            Toast.makeText(this, "This Features is currently unavailable \n In Maintenance mode", Toast.LENGTH_LONG).show()
        }

        binding.mainRecyclerview.setHasFixedSize(true)
        binding.mainRecyclerview.isNestedScrollingEnabled = false
        binding.mainRecyclerview.layoutManager = LinearLayoutManager(this)
        Constants.addDataList()
        val adapter = ParentAdapter(this, parentList = Constants.parentItem)
        binding.mainRecyclerview.adapter = adapter
    }

}
