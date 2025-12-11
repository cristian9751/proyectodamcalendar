package com.cristian.calendarapp.presentation
sealed interface UiState {

    data object Loading : UiState

    data class Error(val errorResourceId: Int) : UiState

    data object Success : UiState

    data object Idle : UiState
}