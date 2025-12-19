package com.cristian.calendarapp.domain.repository

import com.cristian.calendarapp.domain.entity.User

interface UserRepository {

    suspend fun findAuthenticatedUser() : Result<User>
    suspend fun findUserById(id : String) : Result<User>


    suspend fun findUsersByName(name : String) : Result<List<User>>

    suspend fun  updateUser(user : User) : Result<Boolean>
}