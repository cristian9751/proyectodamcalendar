package com.cristian.calendarapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.ROLE
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.usecase.GetUserInfoUseCase
import com.cristian.calendarapp.domain.usecase.UpdateUserInfoUseCase
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.utils.Profile
import com.cristian.calendarapp.presentation.utils.getUiErrorResourceId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
   private val savedStateHandle: SavedStateHandle,
   private val getUserInfoUseCase: GetUserInfoUseCase,
   private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : ViewModel() {
    private val args = Profile.from(savedStateHandle)
    private val _firstname = MutableLiveData<String>()
    val firstname : LiveData<String> = _firstname

    private val _lastname = MutableLiveData<String>()
    val lastname : LiveData<String> = _lastname

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _role = MutableLiveData<String>()
    val role : LiveData<String> = _role


    private val _uiState = MutableLiveData<UiState>()
    val uiState : LiveData<UiState> = _uiState


    init {
        getUserInfoUseCase(args.userId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    result.data?.let {
                        _firstname.postValue(result.data?.firstname)
                        _lastname.postValue(result.data?.lastname)
                        _email.postValue(result.data?.email)
                        _role.postValue(result.data?.role.toString())
                        _uiState.postValue(UiState.Idle)
                    }

                }

                is Resource.Error -> {
                    _uiState.postValue(UiState.Error(errorResourceId = getUiErrorResourceId(error = result.domainError as DomainError)))
                }
                is Resource.Loading ->  {
                    _uiState.postValue(UiState.Loading)
                }
            }

        }.launchIn(viewModelScope)
    }

    fun setFirstName(firstname : String) {
        _firstname.postValue(firstname)
    }

    fun setLastName(lastname : String) {
        _lastname.postValue(lastname)
    }

    fun setEmail(email : String) {
        _email.postValue(email)
    }

    fun updateProfile() {
         val user = User(
            id = args.userId,
            email = _email.value!!,
            firstname = _firstname.value!!,
            lastname = _lastname.value!!,
            role = ROLE.valueOf(_role.value!!)
        )

        updateUserInfoUseCase(user).onEach { result ->
            when(result) {
                is Resource.Error-> {
                    _uiState.postValue(UiState.Error(errorResourceId = getUiErrorResourceId(error = result.domainError as DomainError)))
                }
                is Resource.Loading -> {
                    _uiState.postValue(UiState.Loading)
                }
                is Resource.Success -> {
                    _uiState.postValue(UiState.Success)

                }
            }
        }.launchIn(viewModelScope)
    }







}