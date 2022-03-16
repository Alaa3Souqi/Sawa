package com.aspire.sawa.repositoires

import android.content.SharedPreferences
import com.aspire.sawa.unitls.Constraints.LANGUAGE
import com.aspire.sawa.unitls.Constraints.PINK
import com.aspire.sawa.unitls.Constraints.THEME
import javax.inject.Inject


class SettingsRepositories @Inject constructor(
    private val prefs: SharedPreferences,
    private val editor: SharedPreferences.Editor
) {

    fun getLanguage(): String? =
        prefs.getString(LANGUAGE, null)

    fun updateLanguage(language: String) {
        editor.putString(LANGUAGE, language).apply()
    }

    fun getTheme(): String? =
        prefs.getString(THEME, PINK)

    fun updateTheme(theme: String) {
        editor.putString(THEME, theme).apply()
    }


}