package com.cristian.calendarapp.data.repositoryImpl

import com.cristian.calendarapp.data.local.dao.ProfileDAO
import com.cristian.calendarapp.data.local.entities.toDomain
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val profileDAO: ProfileDAO
) : UserRepository {
    override suspend fun findAuthenticatedUser(): Result<User> {
        try {
            val user = profileDAO.getProfile().first().toDomain()
            return Result.success(user)
        } catch (e : Exception) {
            return Result.failure(DomainError.Unexpected())
        }

    }
}