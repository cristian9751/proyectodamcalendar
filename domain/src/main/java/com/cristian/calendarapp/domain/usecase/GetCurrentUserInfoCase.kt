package com.cristian.calendarapp.domain.usecase

import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentUserInfoCase @Inject constructor(
    private val repository : UserRepository
) {

    operator fun invoke() = flow<Resource<User>> {
        emit(Resource.Loading())
        val result = repository.findAuthenticatedUser()
        if(result.isSuccess) {
            emit(Resource.Success(data = result.getOrNull()))

        } else {
            val error = result.exceptionOrNull() ?: DomainError.Unexpected()
            emit(Resource.Error(error as Exception))
        }

    }.flowOn(Dispatchers.IO)
}