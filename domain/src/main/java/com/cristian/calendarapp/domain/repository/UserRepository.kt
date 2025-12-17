package com.cristian.calendarapp.domain.repository

import com.cristian.calendarapp.domain.entity.User

interface UserRepository {

    suspend fun findAuthenticatedUser() : Result<User>
}