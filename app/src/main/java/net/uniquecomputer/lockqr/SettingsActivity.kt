package net.uniquecomputer.lockqr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

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


             val darkMode = findPreference<SwitchPreferenceCompat>("dark_mode")
             darkMode?.setOnPreferenceChangeListener { preference, newValue ->
                 if (newValue == true) {
                     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                 } else {
                     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                 }
                 true
             }


            val sharedPref = activity?.getSharedPreferences("dark_mode", MODE_PRIVATE)
            val editor = sharedPref?.edit()
            editor?.putBoolean("dark_mode", darkMode?.isChecked!!)
            editor?.apply()


        }
    }
}