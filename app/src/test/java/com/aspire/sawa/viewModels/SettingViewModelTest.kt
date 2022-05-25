package com.aspire.sawa.viewModels

import com.aspire.sawa.repositoires.SettingsRepositories
import com.aspire.sawa.unitls.Constraints.ARABIC
import com.aspire.sawa.unitls.Constraints.BLUE
import com.aspire.sawa.unitls.Constraints.ENGLISH
import com.aspire.sawa.unitls.Constraints.PINK
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class SettingViewModelTest {

    private lateinit var repo: SettingsRepositories
    private lateinit var settingViewModel: SettingViewModel

    @Before
    fun setup() {
        repo = mockk {
            every { getLanguage() } returns ENGLISH
            every { getTheme() } returns BLUE
        }
        settingViewModel = SettingViewModel(repo)
    }

    @Test
    fun `getTheme blue theme in sharedPreferences returns Blue`() {
        val theme = settingViewModel.getTheme()

        assertThat(theme).isEqualTo(BLUE)
    }

    @Test
    fun `getTheme not set theme in sharedPreferences yet returns Pink`() {
        every { repo.getTheme() } returns null

        val theme = settingViewModel.getTheme()

        assertThat(theme).isEqualTo(PINK)
    }

    @Test
    fun `getLanguage not set language to sharedPreferences yet returns english`() {
        val language = settingViewModel.getLanguage()

        assertThat(language).isEqualTo(ENGLISH)
    }

    @Test
    fun `getLanguage Arabic language in sharedPreferences returns arabic`() {
        every { repo.getLanguage() } returns ARABIC

        val language = settingViewModel.getLanguage()

        assertThat(language).isEqualTo(ARABIC)
    }


}