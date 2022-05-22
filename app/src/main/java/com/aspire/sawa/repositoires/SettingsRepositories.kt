package com.aspire.sawa.repositoires

import android.content.SharedPreferences
import com.aspire.sawa.unitls.Constraints.ARABIC
import com.aspire.sawa.unitls.Constraints.ENGLISH
import com.aspire.sawa.unitls.Constraints.LANGUAGE
import com.aspire.sawa.unitls.Constraints.PINK
import com.aspire.sawa.unitls.Constraints.THEME
import java.util.*
import javax.inject.Inject


class SettingsRepositories @Inject constructor(
    private val prefs: SharedPreferences,
) {

    fun getLanguage(): String {

        val language = prefs.getString(LANGUAGE, null)

        language?.let {
            return language
        }

        val deviceLanguage = getLocalLanguage()
        return if (deviceLanguage != ARABIC && deviceLanguage != ENGLISH)
            ENGLISH
        else
            deviceLanguage
    }

    fun updateLanguage(language: String) {
        prefs.edit().putString(LANGUAGE, language).apply()
    }

    fun getTheme(): String? =
        prefs.getString(THEME, PINK)

    fun updateTheme(theme: String) {
        prefs.edit().putString(THEME, theme).apply()
    }

    private fun getLocalLanguage(): String =
        Locale.getDefault().language
}