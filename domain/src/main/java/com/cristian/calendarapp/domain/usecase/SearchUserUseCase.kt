package com.cristian.calendarapp.domain.usecase

import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.ROLE
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    val repository : UserRepository
) {
    operator fun invoke(name : String) = flow<Resource<List<User>>> {
        val currentUser =  repository.findAuthenticatedUser().getOrNull()

        currentUser?.let { currentUser ->
            if(currentUser.role == ROLE.MEMBER) {
                emit(Resource.Error(DomainError.Unauthorized.NotAuhtorized()))
            } else {
                val result = repository.findUsersByName(name)
                if(result.isSuccess) {
                    result.getOrNull()?.let { result ->
                        if(result.isEmpty()) emit(Resource.Error(DomainError.NotFound.UserNotFound()))
                        else emit(Resource.Success(data = result))


                    }
                } else if(result.isFailure) {
                    emit(Resource.Error(result.exceptionOrNull() as Exception))

                }
            }
        }

    }
}