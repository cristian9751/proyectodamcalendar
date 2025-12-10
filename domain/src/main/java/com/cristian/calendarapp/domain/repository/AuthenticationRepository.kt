package com.cristian.calendarapp.domain.repository

import com.cristian.calendarapp.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun signIn(email : String, password: String) : Result<String>
    suspend fun signUp(user: User) : Result<String>
    suspend fun signInWithGoogle() : Result<Unit>
    suspend fun findAuthenticatedUserId(): Result<String>
}