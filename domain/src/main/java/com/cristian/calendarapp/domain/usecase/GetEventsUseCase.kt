package com.cristian.calendarapp.domain.usecase

import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.Event
import com.cristian.calendarapp.domain.repository.AuthenticationRepository
import com.cristian.calendarapp.domain.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
   private val repository: EventRepository
) {

    operator fun invoke(teamId : String) = flow<Resource<List<Event>>> {
        emit(Resource.Loading())
        val result = repository.findByTeamId(teamId )
        if(result.isSuccess)  emit(Resource.Success(data = result.getOrNull()))
        if(result.isFailure)  {
            val error = result.exceptionOrNull() ?: DomainError.Unexpected()
            emit(Resource.Error(error as Exception))
        }
    }.flowOn(Dispatchers.IO)
}