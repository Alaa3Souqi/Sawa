package com.aspire.sawa.ui

import android.util.Patterns
import com.aspire.sawa.R
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): ValidationModel {
        email.trim().let {
            return if (it.isEmpty()) {
                ValidationModel(
                    isValid = false, errorMessageResId = R.string.email_address_is_required
                )
            } else if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                ValidationModel(
                    isValid = false, errorMessageResId = R.string.enter_valid_email_address
                )
            } else ValidationModel(true)
        }
    }
}