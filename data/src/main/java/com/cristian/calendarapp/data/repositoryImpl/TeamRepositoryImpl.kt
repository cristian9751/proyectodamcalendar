package com.cristian.calendarapp.data.repositoryImpl

import com.cristian.calendarapp.data.local.dao.TeamDAO
import com.cristian.calendarapp.data.local.entities.toDomain
import com.cristian.calendarapp.data.local.entities.toLocalEntity
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.entity.Team
import com.cristian.calendarapp.domain.repository.TeamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    val teamDAO: TeamDAO,
) : TeamRepository {
    override suspend fun save(team: Team): Result<Team> {
        try {
            teamDAO.insertTeamWithEvents(team.toLocalEntity())
            return Result.success(team)
        } catch (e : Exception) {
            return Result.failure(exception = DomainError.Unexpected())
        }



    }



    override suspend fun delete(teamId: String): Result<Boolean> {
        val teamEntity = teamDAO.getTeamById(teamId)
        if(teamEntity === null) {
            return Result.failure(exception = DomainError.NotFound.TeamNotFound())
        }
        teamDAO.deleteTeam(teamEntity)
        return Result.success(true)
    }

    override suspend fun getTeams(): Result<List<Team>> {
        val teams = teamDAO.getTeams().map { teams ->
            teams.map { team ->
                teamDAO.getTeamWithEvents(team.id)!!.toDomain()
            }
        }.flowOn(Dispatchers.IO)

        return Result.success(teams.first())

    }

    override suspend fun getById(id : String): Result<Team> {
        val teamEntity =  teamDAO.getTeamById(id)
        if(teamEntity !== null) {
            return Result.success(teamEntity.toDomain())

        }

        return Result.failure(exception = DomainError.NotFound.TeamNotFound())

    }

   override suspend fun getByName(name : String) : Result<Team> {
        val teamEntity = teamDAO.getTeamByName(name)
        if(teamEntity !== null) {
            return Result.success(teamEntity.toDomain())

        }

       return Result.failure(exception = DomainError.NotFound.TeamNotFound())
    }


    override suspend fun getTeamWithEvents(teamId : String) : Result<Team> {
        val team = teamDAO.getTeamWithEvents(teamId)
        if(team !== null) {
            return Result.success(team.toDomain())
        }
        return Result.failure(exception = DomainError.NotFound.TeamNotFound())

    }


}