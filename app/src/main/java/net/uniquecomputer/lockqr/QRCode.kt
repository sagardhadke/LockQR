package net.uniquecomputer.lockqr

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import net.uniquecomputer.lockqr.Utils.Helper
import net.uniquecomputer.lockqr.databinding.ActivityQrcodeBinding
import java.io.File
import java.io.FileOutputStream


class QRCode : AppCompatActivity() {
    private lateinit var binding: ActivityQrcodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkposition()

        binding.colorPicker.setOnClickListener {

            MaterialColorPickerDialog
                .Builder(this)
                .setTitle("Pick Color")
                .setColorShape(ColorShape.SQAURE)
                .setColorSwatch(ColorSwatch._300)
                .setDefaultColor(R.color.black)
                .setColorListener { color, colorHex ->
                    var pickColor = color
                    intent.putExtra("color",pickColor)
                }
                .show()

        }

        binding.colorPickerBg.setOnClickListener {
            ColorPickerDialog
                .Builder(this)        				// Pass Activity Instance
                .setTitle("Pick Theme")           	// Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
                .setDefaultColor(R.color.qr_bg)     // Pass Default Color
                .setColorListener { color, colorHex ->
                    // Handle Color Selection
                    var pickColor = color
                    binding.qrcode.setBackgroundColor(pickColor)
                }
                .show()
        }

    }

    private fun checkposition() {

        when (intent.getIntExtra("position", 0)) {
            0 -> {
                binding.name.isVisible = true
                binding.name.hint = "Text"
                binding.generateText.text = "Generate QR Code for Text"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Your Text"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }
            1 -> {

                val protocol = resources.getStringArray(R.array.protocol)
                val arrayAdapter = ArrayAdapter(this, R.layout.dropdown, protocol)
                binding.autoprotocol.setAdapter(arrayAdapter)
                binding.name.isVisible = true
                binding.protocol.isVisible = true
                binding.note.isVisible = true
                binding.name.hint = "Website"
                binding.generateText.text = "Generate QR Code for Website"
                binding.nameEt.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT
                binding.name.clearFocus()

                binding.name.setOnClickListener {
                    binding.nameEt.hint = "google.com"
                }

                binding.generator.setOnClickListener {
                    val web = binding.nameEt.text.toString().trim()
                    val protocol = binding.autoprotocol.text.toString()
                    var getColor = intent.getIntExtra("color",0)
                    val www = "://www."
                    val text = protocol + www + web
                    checkprotocol()
                    if (!Patterns.WEB_URL.matcher(web).matches()) {
                        binding.name.error = "Please Enter Valid Website Url"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }
                }
            }
            2 -> {
                binding.name.isVisible = true
                binding.address.isVisible = true
                binding.name.hint = "E-mail"
                binding.address.hint = "Content"
                binding.generateText.text = "Generate QR Code for Email"
                binding.nameEt.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    val content = binding.addressEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                        binding.name.error = "Please Enter Valid Email"
                        binding.name.requestFocus()
                    }else if (content.isEmpty()) {
                        binding.address.error = "Please Enter Content"
                        binding.address.requestFocus()
                    }
                    else {
                        binding.name.error = null
                        binding.address.error = null
                        binding.name.clearFocus()
                        binding.address.clearFocus()
                        val bitmap = Helper.generateQrCodemail(text, content, getColor)
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }
            3 -> {
                binding.name.isVisible = true
                binding.address.isVisible = true
                binding.name.hint = "Recipient"
                binding.address.hint = "Message"
                binding.generateText.text = "Generate QR Code for Sms"
                binding.nameEt.inputType = InputType.TYPE_CLASS_NUMBER
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val recipient = binding.nameEt.text.toString().trim()
                    val message = binding.addressEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (!Patterns.PHONE.matcher(recipient).matches()) {
                        binding.name.error = "Please Enter Valid Number"
                        binding.name.requestFocus()
                    }else if (message.isEmpty()) {
                        binding.address.error = "Please Enter Message"
                        binding.address.requestFocus()
                    }
                    else {
                        binding.name.error = null
                        binding.address.error = null
                        binding.name.clearFocus()
                        binding.address.clearFocus()
                        val bitmap = Helper.generateQrCodeSms(recipient, message, getColor)
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }
            4 ->{
                binding.name.isVisible = true
                binding.email.isVisible = true
                binding.name.hint = "Network Name (SSID)"
                binding.email.hint = "Password"
                binding.generateText.text = "Generate QR Code for Wifi"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val network_name = binding.nameEt.text.toString().trim()
                    val pass = binding.emailEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (network_name.isEmpty()) {
                        binding.name.error = "Please Enter Network Name"
                        binding.name.requestFocus()
                    }else if (pass.isEmpty()) {
                        binding.email.error = "Please Enter Your Wifi Password"
                        binding.email.requestFocus()
                    }
                    else {
                        binding.name.error = null
                        binding.address.error = null
                        binding.name.clearFocus()
                        binding.address.clearFocus()
                        val bitmap = Helper.generateQrCodeWifi(network_name, pass, getColor)
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }
            5 ->{
                binding.name.isVisible = true
                binding.name.hint = "Phone Number"
                binding.generateText.text = "Generate QR Code for Phone Number"
                binding.nameEt.inputType = InputType.TYPE_CLASS_NUMBER
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (!Patterns.PHONE.matcher(text).matches()) {
                        binding.name.error = "Please Enter Valid Number"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }
            6 ->{
                binding.name.isVisible = true
                binding.phone.isVisible = true
                binding.email.isVisible = true
                binding.company.isVisible = true
                binding.generateText.text = "Generate QR Code for Contact"
                binding.jobTitle.isVisible = true
                binding.address.isVisible = true

                binding.generator.setOnClickListener {
                    val name = binding.nameEt.text.toString().trim()
                    val phone = binding.phoneEt.text.toString().trim()
                    val email = binding.emailEt.text.toString().trim()
                    val company = binding.companyEt.text.toString().trim()
                    val jobTitle = binding.jobTitleEt.text.toString().trim()
                    val address = binding.addressEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (name.isEmpty()) {
                        binding.name.error = "Please Enter Name"
                        binding.name.requestFocus()
                    }else if (!Patterns.PHONE.matcher(phone).matches()) {
                        binding.phone.error = "Please Enter Phone Number"
                        binding.phone.requestFocus()
                    }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        binding.email.error = "Please Enter Email"
                        binding.email.requestFocus()
                    }else if (company.isEmpty()) {
                        binding.company.error = "Please Enter Company Name"
                        binding.company.requestFocus()
                    }else if (jobTitle.isEmpty()) {
                        binding.jobTitle.error = "Please Enter Job Title"
                        binding.jobTitle.requestFocus()
                    }else if (address.isEmpty()) {
                        binding.address.error = "Please Enter Address"
                        binding.address.requestFocus()
                    }
                    else {
                        binding.name.error = null
                        binding.phone.error = null
                        binding.email.error = null
                        binding.company.error = null
                        binding.jobTitle.error = null
                        binding.address.error = null
                        binding.name.clearFocus()
                        binding.phone.clearFocus()
                        binding.email.clearFocus()
                        binding.company.clearFocus()
                        binding.jobTitle.clearFocus()
                        binding.address.clearFocus()
                        val bitmap = Helper.generateQrCodeContact(name, phone, email, company, jobTitle, address, getColor)
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }
            7->{
                binding.name.isVisible = true
                binding.jobTitle.isVisible = true
                binding.address.isVisible = true
                binding.generateText.text = "Generate QR Code for Calendar"
                binding.name.hint = "Event Name"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val event = binding.nameEt.text.toString().trim()
                    val location = binding.jobTitleEt.text.toString().trim()
                    val address = binding.addressEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (event.isEmpty()) {
                        binding.name.error = "Please Enter Event Name"
                        binding.name.requestFocus()
                    } else if (address.isEmpty()) {
                        binding.address.error = "Please Enter Event Address"
                        binding.address.requestFocus()
                    } else if (location.isEmpty()) {
                        binding.jobTitle.error = "Please Enter Your Location"
                        binding.jobTitle.requestFocus()
                    } else {
                        binding.name.error = null
                        binding.address.error = null
                        binding.jobTitle.error = null
                        binding.name.clearFocus()
                        binding.address.clearFocus()
                        binding.jobTitle.clearFocus()
                        val bitmap = Helper.generateQrCodeCalendar(event, location, address, getColor)
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }
                }
            }

        }

        when(intent.getStringExtra("title")) {
            "Youtube" -> {
                binding.name.isVisible = true
                binding.name.hint = "Youtube Link"
                binding.generateText.text = "Generate QR Code for Youtube"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Youtube Link"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }

            }
            "Whatsapp" -> {
                binding.name.isVisible = true
                binding.protocol.isVisible = false
                binding.note.isVisible = false
                binding.name.hint = "Whatsapp Number"
                binding.generateText.text = "Generate QR Code for Whatsapp"
                binding.nameEt.inputType = InputType.TYPE_CLASS_NUMBER
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Whatsapp Number"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }

            }
            "Facebook" -> {
                binding.name.isVisible = true
                binding.name.hint = "FaceBook"
                binding.address.isVisible = false
                binding.generateText.text = "Generate QR Code for FaceBook"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter FaceBook Username"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }

            }
            "Twitter" -> {
                binding.name.isVisible = true
                binding.name.hint = "Twitter"
                binding.address.isVisible = false
                binding.generateText.text = "Generate QR Code for Twitter"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Twitter Username"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }
            "Linkedin" -> {
                binding.name.isVisible = true
                binding.name.hint = "Linkedin"
                binding.email.isVisible = false
                binding.generateText.text = "Generate QR Code for Linkedin"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Linkedin url"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }
            "Instagram" -> {
                binding.name.isVisible = true
                binding.name.hint = "Instagram Username"
                binding.generateText.text = "Generate QR Code for Instagram"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Instagram Username"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }
            "Telegram" -> {
                binding.phone.isVisible = false
                binding.email.isVisible = false
                binding.company.isVisible = false
                binding.jobTitle.isVisible = false
                binding.address.isVisible = false
                binding.name.isVisible = true
                binding.name.hint = "Telegram Username"
                binding.generateText.text = "Generate QR Code for Telegram"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Your Telegram Username"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }

            "Messenger" -> {
                binding.name.isVisible = true
                binding.jobTitle.isVisible = false
                binding.address.isVisible = false
                binding.name.hint = "Messenger"
                binding.generateText.text = "Generate QR Code for Messenger"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Your Messenger Url"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }

            "Spotify" -> {
                binding.name.isVisible = true
                binding.name.hint = "Spotify Song Url"
                binding.generateText.text = "Generate QR Code for Spotify"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Song Url"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text, getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }

            }
            "Snapchat" -> {
                binding.name.isVisible = true
                binding.protocol.isVisible = false
                binding.note.isVisible = false
                binding.name.hint = "Snapchat Username"
                binding.generateText.text = "Generate QR Code for Snapchat"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Snapchat Username"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text,getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.colorPicker.isVisible = false
                        binding.download.isVisible = true
                        downloadQr()
                        shareQr()
                    }

                }

            }
            "Skype" -> {
                binding.name.isVisible = true
                binding.name.hint = "Skype Profile Link"
                binding.address.isVisible = false
                binding.generateText.text = "Generate QR Code for Skype"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Skype Profile Link"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text,getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }

            }
            "Paypal" -> {
                binding.name.isVisible = true
                binding.name.hint = "Paypal Username"
                binding.address.isVisible = false
                binding.generateText.text = "Generate QR Code for Paypal"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Paypal Username"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text,getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }
            "Pinterest" -> {
                binding.name.isVisible = true
                binding.name.hint = "Pinterest Username"
                binding.email.isVisible = false
                binding.generateText.text = "Generate QR Code for Pinterest"
                binding.nameEt.inputType = InputType.TYPE_CLASS_TEXT
                binding.name.clearFocus()

                binding.generator.setOnClickListener {
                    val text = binding.nameEt.text.toString().trim()
                    var getColor = intent.getIntExtra("color",0)

                    if (binding.nameEt.text!!.isEmpty()) {
                        binding.name.error = "Please Enter Pinterest url"
                        binding.name.requestFocus()
                    } else {
                        binding.name.clearFocus()
                        val bitmap = Helper.generateQrCode(text,getColor)
                        binding.name.error = null
                        binding.qrcode.setImageBitmap(bitmap)
                        binding.generateText.text = "Congratulations! \n You've Created a QR Code!"
                        binding.share.isVisible = true
                        binding.download.isVisible = true
                        binding.colorPicker.isVisible = false
                        downloadQr()
                        shareQr()
                    }

                }
            }
        }

    }

    private fun checkprotocol() {
        val protocol = binding.autoprotocol.text.toString()
        if (protocol.isEmpty()) {
            binding.autoprotocol.error = "Please Select a Protocol"
            binding.autoprotocol.requestFocus()
            Toast.makeText(this, "Please Select a Protocol", Toast.LENGTH_SHORT).show()
        } else {
            binding.autoprotocol.error = null
            Helper.generateQrCode(protocol, 0)
        }

    }

    private fun shareQr() {
        binding.share.setOnClickListener {

            val bitmap = binding.qrcode.drawable.toBitmap()
            val uri = getImageUri(bitmap)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/**"
            intent.putExtra(Intent.EXTRA_TEXT,"I am Generating This Amazing QR Code using Lock QR App \n 1️⃣ 100% FREE & No Ads! \n 2️⃣ Easily create your own unlimited non-expiring QR codes in a matter of seconds \n 3️⃣ Custom Any Text \n 4️⃣ It's free and perfect for websites, contact info,social links and promotional materials. \n 5️⃣  Download QR Code \n 6️⃣ Share QR Code to Any Social Media Platform \n Made in India app. Click the link to download now: https://rb.gy/1rwgg")
            intent.putExtra(Intent.EXTRA_STREAM,uri)
            startActivity(Intent.createChooser(intent,"Share QR Code"))

        }
    }

    private fun downloadQr() {
        binding.download.setOnClickListener {

            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED){
                val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission,1000)
            }else{
                saveQrCode()
            }

        }
    }


    private fun getImageUri(bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(contentResolver,bitmap,"QR Code Generated",null)
        return Uri.parse(path)
    }

    private fun saveQrCode() {
        val bitmap = binding.qrcode.drawable.toBitmap()
        val filename = "${System.currentTimeMillis()}.jpg"
        val externalCacheDir = getExternalFilesDir(null)

        MediaStore.Images.Media.insertImage(contentResolver,bitmap,filename,"QR Code Generated")

        var path = externalCacheDir
        val file = File(path,filename)
        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out)
            out.flush()
            out.close()
            Toast.makeText(this,"QR Code Saved", Toast.LENGTH_LONG).show()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}