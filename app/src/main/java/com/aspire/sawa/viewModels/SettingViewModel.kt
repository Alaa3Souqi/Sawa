package com.aspire.sawa.viewModels

import androidx.lifecycle.ViewModel
import com.aspire.sawa.repositoires.SettingsRepositories
import com.aspire.sawa.unitls.Constraints.PINK
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    private val settingsRepositories: SettingsRepositories
) : ViewModel() {

    fun updateTheme(theme: String) {
        settingsRepositories.updateTheme(theme)
    }

    fun getTheme(): String =
        settingsRepositories.getTheme() ?: PINK

    fun updateLanguage(language: String) {
        settingsRepositories.updateLanguage(language)
    }

    fun getLanguage(): String =
        settingsRepositories.getLanguage()

}