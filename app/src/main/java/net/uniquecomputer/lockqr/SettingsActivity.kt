package net.uniquecomputer.lockqr

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import kotlin.system.exitProcess

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val nightMode = AppCompatDelegate.getDefaultNightMode()
            val themeSwitch = findPreference<SwitchPreferenceCompat>("dark_mode")
            themeSwitch?.isChecked = nightMode == AppCompatDelegate.MODE_NIGHT_YES
            themeSwitch?.isChecked = nightMode == AppCompatDelegate.MODE_NIGHT_NO

            themeSwitch?.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    val alertDialogBuilder = AlertDialog.Builder(context)
                    alertDialogBuilder.setTitle("Change Theme")
                    alertDialogBuilder.setCancelable(false)
                    Toast.makeText(context, "In Maintenance Mode", Toast.LENGTH_SHORT).show()
                    alertDialogBuilder.setMessage("Do you want to change the theme?")
                    alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                        restartApp()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    alertDialogBuilder.show()
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    val alertDialogBuilder = AlertDialog.Builder(context)
                    alertDialogBuilder.setCancelable(false)
                    alertDialogBuilder.setTitle("Change Theme")
                    alertDialogBuilder.setMessage("Do you want to change the theme?")
                    alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    alertDialogBuilder.show()
                }
                true
            }

        }

        private fun restartApp() {
            startActivity(Intent(context, Splash::class.java))
            activity?.finishAffinity()
            onDestroy()
            exitProcess(0)
        }
    }

}