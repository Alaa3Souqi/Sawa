package com.aspire.sawa.repositoires

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class HomeRepositoriesTest {

    lateinit var repo: TimeRepositories

    @Before
    fun setup() {
        repo = TimeRepositories()
    }

    @Test
    fun `test getTimeStringFromDouble take 1000 seconds should return 16 minutes and 40 second`() {
        val timeStringFromDouble = repo.getTimeStringFromSeconds(1000)
        Truth.assertThat(timeStringFromDouble).isEqualTo("00:16:40")
    }

    @Test
    fun `test getTimeStringFromDouble take 5000 seconds should return 1 hour and 23 minutes and 19 second`() {
        val timeStringFromDouble = repo.getTimeStringFromSeconds(5000)
        Truth.assertThat(timeStringFromDouble).isEqualTo("01:23:20")
    }

}