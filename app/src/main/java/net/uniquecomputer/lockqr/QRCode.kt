package net.uniquecomputer.lockqr

import android.R.attr.password
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import net.uniquecomputer.lockqr.databinding.ActivityQrcodeBinding


class QRCode : AppCompatActivity() {
    private lateinit var binding: ActivityQrcodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkposition()

    }

    private fun checkposition() {

        when (val position = intent.getIntExtra("position", 0)) {
            0 -> {
                binding.name.isVisible = true
                Toast.makeText(this, "${position}", Toast.LENGTH_SHORT).show()
            }
            1 -> {
                binding.name.hint = "Address"
                binding.nameEt.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD

                binding.name.isVisible = true
                Toast.makeText(this, "${position}", Toast.LENGTH_SHORT).show()
            }
            2 -> {
                binding.download.isVisible = true
                Toast.makeText(this, "${position}", Toast.LENGTH_SHORT).show()
            }
            8 -> {
                Toast.makeText(this, "$position", Toast.LENGTH_SHORT).show()
            }
        }


    }
}