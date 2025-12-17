package com.cristian.calendarapp.domain.repository

import com.cristian.calendarapp.domain.ROLE

interface RoleRepository {

    suspend fun getAuthenticatedUserRole() : Result<ROLE>
}