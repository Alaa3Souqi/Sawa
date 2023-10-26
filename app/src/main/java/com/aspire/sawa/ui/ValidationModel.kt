package com.aspire.sawa.ui

import androidx.annotation.StringRes

data class ValidationModel(
    val isValid: Boolean,
    val errorMessage: String? = null,
    @StringRes val errorMessageResId: Int? = null
)