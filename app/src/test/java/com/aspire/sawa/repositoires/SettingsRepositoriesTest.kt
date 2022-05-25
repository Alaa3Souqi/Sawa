package com.aspire.sawa.repositoires

import android.content.SharedPreferences
import com.aspire.sawa.unitls.Constraints.ARABIC
import com.aspire.sawa.unitls.Constraints.ENGLISH
import com.aspire.sawa.unitls.Constraints.LANGUAGE
import com.aspire.sawa.unitls.Constraints.PINK
import com.aspire.sawa.unitls.Constraints.THEME
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class SettingsRepositoriesTest {

    lateinit var settingsRepositories: SettingsRepositories
    lateinit var pref: SharedPreferences

    @Before
    fun setup() {
        pref = mockk(relaxed = true) {
            every { getString(LANGUAGE, null) } returns ENGLISH
            every { getString(THEME, PINK) } returns PINK
        }
        settingsRepositories = SettingsRepositories(pref, "France")
    }

    @Test
    fun `getLanguage with ENGLISH arg in pref returns ENGLISH`() {
        val language = settingsRepositories.getLanguage()

        assertThat(language).isEqualTo(ENGLISH)
    }

    @Test
    fun `getLanguage with EMPTY arg in pref and FRANCE in local returns ENGLISH`() {

        pref = mockk(relaxed = true) {
            every { getString(LANGUAGE, null) } returns null
        }

        settingsRepositories = SettingsRepositories(pref, "FRANCE")

        val language = settingsRepositories.getLanguage()

        assertThat(language).isEqualTo(ENGLISH)
    }

    @Test
    fun `getLanguage with ARABIC arg in pref returns ARABIC`() {
        pref = mockk(relaxed = true) {
            every { getString(LANGUAGE, null) } returns ARABIC
        }

        settingsRepositories = SettingsRepositories(pref, "English")

        val language = settingsRepositories.getLanguage()

        assertThat(language).isEqualTo(ARABIC)
    }
}
