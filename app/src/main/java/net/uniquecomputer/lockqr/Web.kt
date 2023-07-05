package net.uniquecomputer.lockqr

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import net.uniquecomputer.lockqr.databinding.ActivityWebBinding
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class Web : AppCompatActivity() {
    lateinit var binding: ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val protocol = resources.getStringArray(R.array.protocol)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown, protocol)
        binding.autoprotocol.setAdapter(arrayAdapter)

//        binding.checkwebid.setOnClickListener {
//            MotionToast.createToast(this,
//                "Failed ☹️",
//                "Please Select a Valid Option!",
//                MotionToastStyle.ERROR,
//                MotionToast.GRAVITY_BOTTOM,
//                MotionToast.LONG_DURATION,
//                ResourcesCompat.getFont(this,R.font.poppins_semibold))
//            validate()
//        }

    }

//    private fun validate() {
//
//
//        if (binding.editText.text.isEmpty()){
//            binding.editText.error = "Please Enter a Valid Web Address"
//            binding.editText.requestFocus()
//        }else{
//            binding.editText.clearFocus()
//            binding.editText.isCursorVisible = false
//        }
//    }


}