package net.uniquecomputer.lockqr

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import net.uniquecomputer.lockqr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkTheme()

        binding.mainRecyclerview.setHasFixedSize(true)
        binding.mainRecyclerview.isNestedScrollingEnabled = false
        binding.mainRecyclerview.layoutManager = LinearLayoutManager(this)
//        Constants.addDataList()

        val parentItem = ArrayList<ParentItem>()

        val childItem1 = ArrayList<ChildItem>()
        childItem1.add(ChildItem("Text", R.drawable.text))
        childItem1.add(ChildItem("Website", R.drawable.website))
        childItem1.add(ChildItem("Email", R.drawable.email))
        childItem1.add(ChildItem("SMS", R.drawable.message))
        childItem1.add(ChildItem("Wi-Fi", R.drawable.wifi))
        childItem1.add(ChildItem("Phone", R.drawable.call))
        childItem1.add(ChildItem("Contact", R.drawable.contact))
        childItem1.add(ChildItem("Calender", R.drawable.calendar))

        parentItem.add(ParentItem("General", childItem1))

        val childItem2 = ArrayList<ChildItem>()
        childItem2.add(ChildItem("Youtube", R.drawable.youtube))
        childItem2.add(ChildItem("Whatsapp", R.drawable.whatsapp))
        childItem2.add(ChildItem("Facebook", R.drawable.facebook))
        childItem2.add(ChildItem("Twitter", R.drawable.twitter))
        childItem2.add(ChildItem("Linkedin", R.drawable.linkedin))
        childItem2.add(ChildItem("Instagram", R.drawable.instagram))
        childItem2.add(ChildItem("Telegram", R.drawable.telegram))
        childItem2.add(ChildItem("Messenger", R.drawable.messenger))

        parentItem.add(ParentItem("Social", childItem2))

        val child3 = ArrayList<ChildItem>()
        child3.add(ChildItem("Spotify", R.drawable.spotify))
        child3.add(ChildItem("Snapchat", R.drawable.snapchat))
        child3.add(ChildItem("Skype", R.drawable.skype))
        child3.add(ChildItem("Paypal", R.drawable.paypal))
        child3.add(ChildItem("Pinterest", R.drawable.pinterest))

        parentItem.add(ParentItem("Other", child3))

        val adapter = ParentAdapter(this, parentList = parentItem)
        binding.mainRecyclerview.adapter = adapter

        binding.settings.setOnClickListener {
            chooseThemeDialog()
        }

    }

    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }

    class MyPreferences(context: Context?) {
        companion object {
            private const val DARK_STATUS = "io.github.manuelernesto.DARK_STATUS"
        }
        private val preferences = PreferenceManager.getDefaultSharedPreferences(context!!)
        var darkMode = preferences.getInt(DARK_STATUS, 2)
            set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()
    }

    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_theme_text))
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = MyPreferences(this).darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode = 1
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(this).darkMode = 2
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }

}
