package com.cristian.calendarapp.domain.usecase

import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val repository : AuthenticationRepository
) {
    operator fun invoke() = flow<Resource<String>> {
        emit(Resource.Loading())
        val result = repository.findAuthenticatedUserId()
        if(result.isSuccess) Resource.Success(data = result.getOrDefault(""))
        val error = result.exceptionOrNull() ?: DomainError.Unexpected()
        emit(Resource.Error(error as Exception ))
    }
}