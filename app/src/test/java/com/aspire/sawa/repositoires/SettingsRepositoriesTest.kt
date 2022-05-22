package com.aspire.sawa.repositoires

import android.content.SharedPreferences
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.aspire.sawa.unitls.Constraints.ARABIC
import com.aspire.sawa.unitls.Constraints.ENGLISH
import com.aspire.sawa.unitls.Constraints.LANGUAGE
import com.aspire.sawa.unitls.Constraints.PINK
import com.aspire.sawa.unitls.Constraints.THEME
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
        settingsRepositories = SettingsRepositories(pref)
    }

    @Test
    fun `getLanguage with ENGLISH arg in pref returns ENGLISH`() {
        val language = settingsRepositories.getLanguage()

        assertThat(language).isEqualTo(ENGLISH)
    }

    @Test
    fun `getLanguage with EMPTY arg in pref and FRANCE in local returns ENGLISH`() {
        pref = mockk(relaxed = true) {
            every { getString(LANGUAGE, null) } returns "FRANCE"
        }
        settingsRepositories = SettingsRepositories(pref)

        val language = settingsRepositories.getLanguage()

        assertThat(language).isEqualTo(ENGLISH)
    }

    @Test
    fun `getLanguage with ARABIC arg in pref returns ARABIC`() {
        pref = mockk(relaxed = true) {
            every { getString(LANGUAGE, null) } returns ARABIC
        }

        settingsRepositories = SettingsRepositories(pref)

        val language = settingsRepositories.getLanguage()

        assertThat(language).isEqualTo(ARABIC)
    }
}
