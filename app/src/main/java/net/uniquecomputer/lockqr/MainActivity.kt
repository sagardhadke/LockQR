package net.uniquecomputer.lockqr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import net.uniquecomputer.lockqr.Utils.Constants
import net.uniquecomputer.lockqr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainRecyclerview.setHasFixedSize(true)
        binding.mainRecyclerview.isNestedScrollingEnabled = false
        binding.mainRecyclerview.layoutManager = LinearLayoutManager(this)
        Constants.addDataList()
        val adapter = ParentAdapter(this, parentList = Constants.parentItem)
        binding.mainRecyclerview.adapter = adapter
    }
}
