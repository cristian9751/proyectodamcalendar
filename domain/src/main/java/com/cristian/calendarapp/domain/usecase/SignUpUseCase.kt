package com.cristian.calendarapp.domain.usecase

import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    val repository : AuthenticationRepository
) {

    operator fun invoke(user : User) = flow<Resource<String>> {
        emit(Resource.Loading())
        val result = repository.signUp(user)
        if(result.isSuccess) emit(Resource.Success(data = result.getOrNull()))
        emit(Resource.Error(error = result.exceptionOrNull() as Exception))
    }.flowOn(
        Dispatchers.IO
    )
}