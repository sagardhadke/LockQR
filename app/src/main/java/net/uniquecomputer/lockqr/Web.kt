package net.uniquecomputer.lockqr

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import net.uniquecomputer.lockqr.databinding.ActivityWebBinding

class Web : AppCompatActivity() {
    lateinit var binding: ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set values to the spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.spinnerItems, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.setSelection(0)

        binding.checkwebid.setOnClickListener {
            Toast.makeText(this, "Please Select a Valid Option", Toast.LENGTH_SHORT).show()
            validate()
        }

    }

    private fun validate() {

        if (binding.spinner.selectedItemPosition == 0){
            Toast.makeText(this, "Please Select a Valid Option", Toast.LENGTH_SHORT).show()
            binding.spinner.requestFocus()
        }else{
            binding.spinner.selectedItemPosition == 1
            binding.spinner.setBackgroundColor(resources.getColor(R.color.green))

        }

    }
}