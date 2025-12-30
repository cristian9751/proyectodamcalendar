package com.cristian.calendarapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.usecase.CreateEventUseCase
import com.cristian.calendarapp.domain.usecase.GetEventsUseCase
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.UiState.*
import com.cristian.calendarapp.presentation.model.CalendarEvent
import com.cristian.calendarapp.presentation.model.toCalendarEvent
import com.cristian.calendarapp.presentation.model.toDomain
import com.cristian.calendarapp.presentation.utils.Calendar
import com.cristian.calendarapp.presentation.utils.getUiErrorResourceId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val  savedStateHandle: SavedStateHandle,
    private val getEventsUseCase: GetEventsUseCase,
    private val createEventUseCase: CreateEventUseCase
) : ViewModel() {
    private val args = Calendar.from(savedStateHandle)

    private val _events = MutableLiveData<List<CalendarEvent>>()
    val events : LiveData<List<CalendarEvent>> = _events

    val teamId = args.teamId

    private val _uiState = MutableLiveData<UiState>()
    val uiState : LiveData<UiState> = _uiState

    init {
        getEventsUseCase(teamId).onEach { result ->
            when(result) {
                is Resource.Error -> {
                    _uiState.value = UiState.Error(getUiErrorResourceId(result.domainError as DomainError))
                    _events.value = emptyList()
                }
                is Resource.Loading ->  {
                    _uiState.value = UiState.Loading
                }
                is Resource.Success -> {
                    result.data?.let {
                        val currentEvents = it.map { event ->
                            event.toCalendarEvent()
                        }
                        _events.value = currentEvents
                    }
                    _uiState.value = UiState.Success
                }
            }

        }.launchIn(viewModelScope)
    }


    fun createEvent(event : CalendarEvent) {
        createEventUseCase(event.toDomain())
            .onEach { result ->
                when(result) {
                    is Resource.Error -> {

                        _uiState.value = Error(getUiErrorResourceId(result.domainError as DomainError))
                    }

                    is Resource.Loading -> {
                        _uiState.value = Loading
                    }
                    is Resource.Success -> {
                        _uiState.value = Success
                    }
                }

            }
            .launchIn(viewModelScope)
    }


}