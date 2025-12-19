package com.cristian.calendarapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.usecase.SearchUserUseCase
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.model.UserModel
import com.cristian.calendarapp.presentation.model.fromDomain
import com.cristian.calendarapp.presentation.utils.getUiErrorResourceId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
   private val  searchUserUseCase: SearchUserUseCase
) : ViewModel() {

    private val _uiState  = MutableLiveData<UiState>()

    val uiState : LiveData<UiState> = _uiState

    private val _users = MutableLiveData<List<UserModel>>()
    val users : LiveData<List<UserModel>> = _users


    fun searchUser(name : String) {
      searchUserUseCase(name).onEach { result ->
          when(result) {
              is Resource.Error -> {
                  _users.value?.let { users ->
                      if(users.isNotEmpty()) _users.value = emptyList()
                  }
                  _uiState.value = UiState.Error(errorResourceId = getUiErrorResourceId(result.domainError as DomainError))
              }
              is Resource.Loading ->{
                  _uiState.value = UiState.Loading
              }
              is Resource.Success -> {
                  _users.value = result.data?.map { user ->
                      user.fromDomain()
                  }

                  _uiState.value = UiState.Success
              }
          }
      }.launchIn(viewModelScope)
    }

}