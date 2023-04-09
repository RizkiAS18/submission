package com.example.githubuserapp.ui.theme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.ui.ViewModelFactory
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivityThemeSettingBinding
import com.google.android.material.switchmaterial.SwitchMaterial


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ThemeSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
        val pref = SettingPreference.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref))[ThemeSettingViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        themeViewModel.getThemeSetting().observe(this) {isDarkModeActive: Boolean ->
            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
        switchTheme.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }
}