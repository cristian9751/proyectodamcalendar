package com.cristian.calendarapp.data.repositoryImpl

import com.cristian.calendarapp.data.local.dao.ProfileDAO
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.ROLE
import com.cristian.calendarapp.domain.repository.RoleRepository
import javax.inject.Inject

class RoleRepositoryImpl  @Inject constructor(
    val profileDAO: ProfileDAO
) : RoleRepository {
    override suspend fun getAuthenticatedUserRole(): Result<ROLE> {
        try{
            return Result.success(profileDAO.getRole())
        } catch (e : Exception) {
            return Result.failure(DomainError.Unexpected())
        }
    }
}