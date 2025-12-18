package com.cristian.calendarapp.domain.usecase

import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.repository.AuthenticationRepository
import com.cristian.calendarapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository : UserRepository,
    private val authRepository : AuthenticationRepository
) {

    operator fun invoke(userId : String) = flow<Resource<User>> {
        emit(Resource.Loading())
        val currentUserId =  authRepository.findAuthenticatedUserId()
        val result = if(currentUserId.equals(userId)) repository.findAuthenticatedUser() else repository.findUserById(userId)
        if(result.isSuccess) {
            emit(Resource.Success(data = result.getOrNull()))

        } else {
            val error = result.exceptionOrNull() ?: DomainError.Unexpected()
            emit(Resource.Error(error as Exception))
        }

    }.flowOn(Dispatchers.IO)
}