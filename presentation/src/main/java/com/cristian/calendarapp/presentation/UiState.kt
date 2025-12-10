package com.cristian.calendarapp.presentation
sealed interface UiState {

    data object Loading : UiState

    data class Error(val errorResourceId: Int) : UiState

    data class Success<T>(val data: T) : UiState

    data object Idle : UiState
}