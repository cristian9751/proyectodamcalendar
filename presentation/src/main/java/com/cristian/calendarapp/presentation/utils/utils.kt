package com.cristian.calendarapp.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
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

        is DomainError.NotAuthenticated -> {
            R.string.not_authenticated_error
        }
    }

}

@Composable
inline fun <reified  T : ViewModel> NavController.sharedViewModelOnSubGraph() : T {

    val currentNavBackStackEntry = this.currentBackStackEntry !!
    val parentRoute : String = currentNavBackStackEntry.destination.parent?.route ?: return hiltViewModel<T>()
    val parentBackStackEntry = remember(parentRoute) {
        this.getBackStackEntry(parentRoute)
    }

    return hiltViewModel<T>(parentBackStackEntry)
}