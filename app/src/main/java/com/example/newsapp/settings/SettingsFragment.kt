package com.example.newsapp.settings

import android.os.Bundle
import androidx.preference.DropDownPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.newsapp.R

class SettingsFragment : PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}