package com.cristian.calendarapp.presentation.utils

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class Profile(val userId : String, val currentUserId : String) {
    companion object {
        fun from(savedStateHandle: SavedStateHandle) : Profile {
            return savedStateHandle.toRoute<Profile>()
        }
    }
}


@Serializable
data class Calendar(val teamId : String) {
    companion object {
        fun from(savedStateHandle: SavedStateHandle) : Calendar{
            return savedStateHandle.toRoute<Calendar>()
        }
    }
}