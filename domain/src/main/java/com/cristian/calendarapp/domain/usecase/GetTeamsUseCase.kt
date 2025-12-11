package com.cristian.calendarapp.domain.usecase

import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.Team
import com.cristian.calendarapp.domain.repository.TeamRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTeamsUseCase @Inject constructor(
    val repository : TeamRepository
) {

    operator fun invoke()  = flow<Resource<List<Team>>>{
        emit(Resource.Loading())
        val result = repository.getTeams()
        if(result.isSuccess) emit(Resource.Success(data = result.getOrNull()))
        if(result.isFailure)  {
            val error = result.exceptionOrNull() ?: DomainError.Unexpected()
            emit(Resource.Error(error as Exception))
        }
    }
}