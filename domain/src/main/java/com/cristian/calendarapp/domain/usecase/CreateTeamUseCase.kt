package com.cristian.calendarapp.domain.usecase

import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.ROLE
import com.cristian.calendarapp.domain.Resource
import com.cristian.calendarapp.domain.entity.Team
import com.cristian.calendarapp.domain.repository.RoleRepository
import com.cristian.calendarapp.domain.repository.TeamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateTeamUseCase @Inject constructor(
    private val repository : TeamRepository,
    private val roleRepository: RoleRepository
) {

    operator fun invoke(team : Team) = flow<Resource<Team>> {
        emit(Resource.Loading())
        val role = roleRepository.getAuthenticatedUserRole().getOrNull()
        if(role !== ROLE.ADMIN) {
            emit(Resource.Error(error = DomainError.Unauthorized()))
        } else {
            if(repository.getByName(team.name).isSuccess) {
                emit(Resource.Error(error = DomainError.DuplicatedData.TeamAlreadyExists()))
            } else {
                val result = repository.save(team)
                if(result.isSuccess)  emit(Resource.Success(data = result.getOrNull()))
                if(result.isFailure)  {
                    val error = result.exceptionOrNull() ?: DomainError.Unexpected()
                    emit(Resource.Error(error as Exception))
                }
            }
        }


    }.flowOn(Dispatchers.IO)
}