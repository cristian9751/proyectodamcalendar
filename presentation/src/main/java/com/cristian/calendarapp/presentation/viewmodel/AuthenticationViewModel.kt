package com.cristian.calendarapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.ROLE
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.usecase.SignInUseCase
import com.cristian.calendarapp.domain.usecase.SignUpUseCase
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.utils.getUiErrorResourceId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val  signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase
) : ViewModel() {


    private val _uiState = MutableLiveData<UiState>()
    val uiState : LiveData<UiState> = _uiState
    private val _firstname = MutableLiveData<String>()
    val firstname : LiveData<String> = _firstname

    private val _lastname = MutableLiveData<String>()
    val lastname : LiveData<String> = _lastname

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    fun setFirstname(firstname : String) {
        _firstname.value = firstname
    }

    fun setLastName(lastname : String) {
        _lastname.value = lastname
    }

    fun setEmail(email : String) {
        _email.value = email
    }

    fun setPassword(password : String) {
        _password.value = password
    }



    fun signIn() {
        signInUseCase.invoke(_email.value!!, _password.value!!).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _uiState.value = UiState.Loading
                }
                is Resource.Success -> {
                    _uiState.value = UiState.Success
                }
                is Resource.Error -> {
                    _uiState.value = UiState.Error(errorResourceId = getUiErrorResourceId(result.domainError as DomainError))
                }
            }

        }.launchIn(viewModelScope)
    }



    fun signUp() {
        val user : User = User(
            email = _email.value!!,
            password = _password.value!!,
            firstname = _firstname.value!!,
            lastname = _lastname.value!!,
            role = ROLE.MEMBER

        )
        signUpUseCase.invoke(user).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _uiState.value = UiState.Loading
                }
                is Resource.Success -> {
                    _uiState.value = UiState.Success
                }
                is Resource.Error -> {
                    _uiState.value = UiState.Error(errorResourceId = getUiErrorResourceId(result.domainError as DomainError))
                }
            }
        }.launchIn(viewModelScope)
    }



}