package com.cristian.calendarapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.ROLE
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.usecase.GetSessionUseCase
import com.cristian.calendarapp.domain.usecase.GetUserInfoUseCase
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.utils.getUiErrorResourceId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState : LiveData<UiState> = _uiState

    private val _currentUserId = MutableLiveData<String>()
    val currentUserId : LiveData<String> = _currentUserId

    private val _isAdministrator = MutableLiveData(false)
    val isAdministrator : LiveData<Boolean> = _isAdministrator



    init {
        getSessionUseCase.invoke()
            .onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                    is Resource.Success -> {
                        _currentUserId.value = result.data.toString()
                        getUserInfoUseCase(_currentUserId.value.toString()).filter { result ->
                            result is Resource.Success
                        }.onEach { result ->
                            result.data?.role.let { role ->
                                _isAdministrator.value = role !== ROLE.MEMBER
                            }
                        }.launchIn(viewModelScope)
                        _uiState.value = UiState.Success
                    }
                    is Resource.Error -> {
                        _uiState.value = UiState.Error(errorResourceId = getUiErrorResourceId(result.domainError as DomainError))
                    }
                }
            }
            .launchIn(viewModelScope)
    }


}