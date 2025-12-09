package com.cristian.calendarapp.presentation.utils

import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.presentation.R

fun getUiErrorResourceId(error : DomainError) : Int {
    return when (error) {
        is DomainError.Unexpected -> {
            R.string.unexpected_error
        }

        is DomainError.DuplicatedData.EmailAlreadyExists -> {
            R.string.email_exists
        }

        is DomainError.InvalidData.InvalidEmail -> {
            R.string.email_invalid
        }

        is DomainError.InvalidCredential -> {
            R.string.invalid_credentials_error
        }
    }

}