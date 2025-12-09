package com.cristian.calendarapp.presentation


interface IUiState {
    val isLoading : Boolean
    val errorResourceId : Int

    fun isError() : Boolean

}
data class UiState<T> (
    override val isLoading : Boolean = false,
    override val errorResourceId : Int = -100,
    val data : T? = null
) : IUiState{

    override fun isError() : Boolean {
        return errorResourceId != -100
    }

}
