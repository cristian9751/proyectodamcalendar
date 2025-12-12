package com.cristian.calendarapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.usecase.CreateTeamUseCase
import com.cristian.calendarapp.domain.usecase.GetTeamsUseCase
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.model.TeamModel
import com.cristian.calendarapp.presentation.model.toDomain
import com.cristian.calendarapp.presentation.model.toModel
import com.cristian.calendarapp.presentation.utils.getUiErrorResourceId
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.UUID

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val getTeamsUseCase: GetTeamsUseCase,
    private val createTeamUseCase: CreateTeamUseCase,

) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState : LiveData<UiState> = _uiState

    private val _teams = MutableLiveData<List<TeamModel>>()
    val teams : LiveData<List<TeamModel>> = _teams




    init {
      getTeams()
    }


    fun getTeams() {
        getTeamsUseCase()
            .onEach { resource ->
                when(resource) {
                    is Resource.Loading -> {
                        _uiState.value = UiState.Loading
                    }

                    is Resource.Error -> {
                        _uiState.value = UiState.Error(errorResourceId = getUiErrorResourceId(resource.domainError as DomainError))

                    }
                    is Resource.Success-> {
                        _teams.value = resource.data?.map {team ->
                            team.toModel()
                        }

                        _uiState.value = UiState.Success



                    }
                }

            }
            .launchIn(viewModelScope)
    }


    fun addTeam(name : String, description : String) {
        val team = TeamModel(name = name, description = description, id = UUID.randomUUID().toString())
        val currentTeams  = _teams.value.orEmpty().toMutableList()
        currentTeams.add(team)
        _teams.value = currentTeams
        createTeamUseCase(team.toDomain()).onEach { resource ->
            when(resource) {
                is Resource.Loading -> {
                    _uiState.value = UiState.Loading
                }
                is Resource.Error -> {
                    removeTeam(team)
                    _uiState.value = UiState.Error(errorResourceId = getUiErrorResourceId(resource.domainError as DomainError))
                }

                is Resource.Success -> {
                    _uiState.value = UiState.Success
                }
            }
        }.launchIn(viewModelScope)

    }


    private fun removeTeam(team : TeamModel) {
        val currentTeams = _teams.value.orEmpty().toMutableList()
        currentTeams.remove(team)
        _teams.value = currentTeams
    }
}

