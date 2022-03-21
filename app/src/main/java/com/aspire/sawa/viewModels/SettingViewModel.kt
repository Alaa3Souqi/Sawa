package com.aspire.sawa.viewModels

import androidx.lifecycle.ViewModel
import com.aspire.sawa.repositoires.SettingsRepositories
import com.aspire.sawa.unitls.Constraints.ARABIC
import com.aspire.sawa.unitls.Constraints.ENGLISH
import com.aspire.sawa.unitls.Constraints.PINK
import java.util.Locale.getDefault
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    private val settingsRepositories: SettingsRepositories
) :
    ViewModel() {

    fun updateTheme(theme: String) {
        settingsRepositories.updateTheme(theme)
    }

    fun getTheme(): String =
        settingsRepositories.getTheme() ?: PINK

    fun updateLanguage(language: String) {
        settingsRepositories.updateLanguage(language)
    }

    fun getLanguage(): String =
        getLanguageSafeCall(settingsRepositories.getLanguage())

    private fun getLanguageSafeCall(language: String?): String {
        val lang = language ?: getLocalLanguage()

        return if (lang != ARABIC && lang != ENGLISH)
            ENGLISH
        else
            lang
    }

    private fun getLocalLanguage(): String =
        getDefault().language
}