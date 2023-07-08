package net.uniquecomputer.lockqr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.hbb20.CountryCodePicker
import net.uniquecomputer.lockqr.databinding.ActivitySmsBinding
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class Sms : AppCompatActivity() {
   private lateinit var binding : ActivitySmsBinding
    lateinit var ccp : CountryCodePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySmsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.smsGenerate.setOnClickListener {
            val sms = binding.smsNumber.text.toString().trim()
            val message = binding.smsMessage.text.toString()
            if (sms.length>10&&sms.isEmpty()){
                binding.smsNumber.error = "Enter a valid number"
                MotionToast.createToast(this,
                    "Failed ☹️",
                    "Please Enter a Valid Number!",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this,R.font.poppins_semibold))

            }else if (message.isEmpty()) {
                binding.smsMessage.error = "Enter a valid message"
                MotionToast.createToast(
                    this,
                    "Failed ☹️",
                    "Please Enter a Valid Message!",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, R.font.poppins_semibold)
                )
            }else{
                binding.smsNumber.error = null
                binding.smsMessage.error = null
                MotionToast.createToast(
                    this,
                    "Success ☺️",
                    "QR Code Generated Successfully!",
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, R.font.poppins_semibold)
                )
            }
        }

    }


}