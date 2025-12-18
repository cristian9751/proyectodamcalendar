package com.cristian.calendarapp.domain.usecase

import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.ROLE
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.repository.AuthenticationRepository
import com.cristian.calendarapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(user : User) = flow<Resource<Boolean>> {
        emit(Resource.Loading())
        val currentUser =  repository.findAuthenticatedUser().getOrNull()


       currentUser?.let { currentUser ->
           if(currentUser.id !== user.id) {
               if(currentUser.role !== ROLE.ADMIN) emit(Resource.Error(DomainError.Unauthorized()))
               val findByIdResult = repository.findUserById(user.id)
               if(findByIdResult.isFailure) {
                   val error = findByIdResult.exceptionOrNull() ?: DomainError.Unexpected()
                   emit(Resource.Error(error as Exception))
               }
           }
           val result = repository.updateUser(user)
           if(result.isSuccess) {
               emit(Resource.Success(data = result.getOrNull()))

           } else {
               val error = result.exceptionOrNull() ?: DomainError.Unexpected()
               emit(Resource.Error(error as Exception))
           }
       }




    }.flowOn(Dispatchers.IO)
}