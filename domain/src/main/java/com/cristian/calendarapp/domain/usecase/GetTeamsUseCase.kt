package com.cristian.calendarapp.domain.usecase

import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.Team
import com.cristian.calendarapp.domain.repository.TeamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetTeamsUseCase @Inject constructor(
    private val repository : TeamRepository
) {

    operator fun invoke()  = flow<Resource<List<Team>>>{
        val result = repository.getTeams()

        if(result.isFailure) {
            val error = result.exceptionOrNull() ?: DomainError.Unexpected()
            emit(Resource.Error(error as Exception))

        }

        val  resultFlow = result.getOrNull()!!

         resultFlow
            .onStart {
                emit(Resource.Loading())
            }
            .map { teams ->
                Resource.Success(data = teams)
            }
            .catch { e ->
                val error = (e as? DomainError) ?: DomainError.Unexpected()
                emit(Resource.Error(error as Exception))
            }

            .flowOn(Dispatchers.IO)
             .collect { resource ->
                 emit(resource)
             }

    }.flowOn(Dispatchers.IO)
}