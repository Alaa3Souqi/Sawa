package com.aspire.sawa.repositoires

import android.content.SharedPreferences
import com.aspire.sawa.unitls.Constraints.ARABIC
import com.aspire.sawa.unitls.Constraints.ENGLISH
import com.aspire.sawa.unitls.Constraints.LANGUAGE
import com.aspire.sawa.unitls.Constraints.PINK
import com.aspire.sawa.unitls.Constraints.THEME
import javax.inject.Inject
import javax.inject.Named


class SettingsRepositories @Inject constructor(
    private val prefs: SharedPreferences,
    @Named("language") private val localLanguage: String
) {

    fun getLanguage(): String {

        val language = prefs.getString(LANGUAGE, null)

        language?.let {
            return language
        }

        return if (localLanguage != ARABIC && localLanguage != ENGLISH)
            ENGLISH
        else
            localLanguage
    }

    fun updateLanguage(language: String) =
        prefs.edit().putString(LANGUAGE, language).commit()


    fun getTheme(): String? =
        prefs.getString(THEME, PINK)

    fun updateTheme(theme: String) =
        prefs.edit().putString(THEME, theme).commit()

}