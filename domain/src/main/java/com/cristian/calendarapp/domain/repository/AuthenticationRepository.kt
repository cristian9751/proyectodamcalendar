package com.cristian.calendarapp.domain.repository

import com.cristian.calendarapp.domain.entity.User

interface AuthenticationRepository {
    suspend fun signIn(email : String, password: String) : Result<String>
    suspend fun signUp(user: User) : Result<String>
    suspend fun signInWithGoogle() : Result<Boolean>
}