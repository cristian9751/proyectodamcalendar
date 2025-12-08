package com.cristian.calendarapp.presentation


data class UiState<T> (
    val isLoading : Boolean = false,
    val error: String = "",
    val data : T? = null
) {

    fun isError() : Boolean {
        return error.isNotEmpty()
    }

}