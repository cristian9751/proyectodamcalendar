package com.cristian.calendarapp.domain.usecase

import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository : AuthenticationRepository
) {

    operator fun invoke(email : String, password : String) = flow<Resource<String>> {
        emit(Resource.Loading())
        val result = repository.signIn(email, password)
        if(result.isSuccess)  emit(Resource.Success(data = result.getOrNull()))
        emit(Resource.Error(error = result.exceptionOrNull() as Exception))
    }.flowOn(Dispatchers.IO)

}