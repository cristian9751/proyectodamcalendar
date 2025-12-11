package com.cristian.calendarapp.domain.repository

import com.cristian.calendarapp.domain.entity.Team

interface TeamRepository {

    suspend fun save(team : Team) : Result<Team>


    suspend fun delete(teamId : String) : Result<Boolean>

    suspend fun getTeams() : Result<List<Team>>

    suspend fun getById(id : String) : Result<Team>
    suspend fun getByName(name : String) : Result<Team>
}