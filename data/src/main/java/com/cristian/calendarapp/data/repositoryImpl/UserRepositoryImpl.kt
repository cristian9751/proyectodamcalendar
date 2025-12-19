package com.cristian.calendarapp.data.repositoryImpl

import com.cristian.calendarapp.data.local.dao.ProfileDAO
import com.cristian.calendarapp.data.local.entities.ProfileEntity
import com.cristian.calendarapp.data.local.entities.toDomain
import com.cristian.calendarapp.data.local.entities.toEntity
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.entity.User
import com.cristian.calendarapp.domain.repository.UserRepository
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val profileDAO: ProfileDAO,
    private val postgrest: Postgrest
) : UserRepository {
    override suspend fun findAuthenticatedUser(): Result<User> {
        try {
            val user = profileDAO.getProfile().first().toDomain()
            return Result.success(user)
        } catch (e : Exception) {
            return Result.failure(DomainError.Unexpected())
        }

    }

    override suspend fun findUserById(id: String): Result<User> {
        try {
            val result = postgrest.from("user_details").select {
                filter {
                    eq("user_id", id)
                }
            }.decodeSingle<ProfileEntity>()
            return Result.success(result.toDomain())
        } catch (e: Exception) {
            return Result.failure(DomainError.NotFound.UserNotFound())
        }


    }

    override suspend fun findUsersByName(name: String): Result<List<User>> {
        try {
            val result = postgrest.from(
                table = "user_details",
                schema = "public"
            ).select {
                filter {
                   or {
                       ilike("firstname", "%$name%")
                       ilike("lastname", "%$name%")
                   }
                }
            }.decodeList<ProfileEntity>()

            return Result.success(ProfileEntity.toDomainList(result))
        } catch (e : Exception) {
           return Result.failure(DomainError.Unexpected())
        }
    }

    override suspend fun updateUser(user : User): Result<Boolean> {
        try {
            profileDAO.updateProfile(user.toEntity().apply { this.isSynchronized = false })
            return Result.success(true)
        } catch (e  : Exception) {
            return Result.failure(DomainError.Unexpected())
        }
    }
}