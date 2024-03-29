package com.example.githubuserapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.ui.theme.SettingPreference
import com.example.githubuserapp.ui.theme.ThemeSettingViewModel

class ViewModelFactory(private val pref: SettingPreference): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ThemeSettingViewModel::class.java)){
            return ThemeSettingViewModel(pref) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}