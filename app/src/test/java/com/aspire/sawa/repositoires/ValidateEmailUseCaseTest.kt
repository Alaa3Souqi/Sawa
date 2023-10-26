package com.aspire.sawa.repositoires

import com.aspire.sawa.ui.ValidateEmailUseCase
import com.google.common.truth.Truth
import org.junit.Test


class ValidateEmailUseCaseTest {
    val validateEmailUseCase = ValidateEmailUseCase()

    @Test
    fun `empty email should return is valid false`() {
        val useCase = validateEmailUseCase("")
        Truth.assertThat(useCase.isValid).isEqualTo(false)
    }
}