package com.cristian.calendarapp.data.repositoryImpl

import androidx.lifecycle.map
import com.cristian.calendarapp.data.local.dao.TeamDAO
import com.cristian.calendarapp.data.local.entities.toDomain
import com.cristian.calendarapp.data.local.entities.toEntity
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
        val savedTeam = teamDAO.insertTeam(team.toEntity()).toDomain()
        return Result.success(savedTeam)


    }



    override suspend fun delete(teamId: String): Result<Boolean> {
        val teamEntity = teamDAO.getTeamById(teamId)
        if(teamEntity === null) {
            return Result.failure(exception = DomainError.NotFound())
        }
        teamDAO.deleteTeam(teamEntity)
        return Result.success(true)
    }

    override suspend fun getTeams(): Result<List<Team>> {
        val teams = teamDAO.getTeams().map { teams ->
            teams.map { team ->
                team.toDomain()
            }
        }.flowOn(Dispatchers.IO)

        return Result.success(teams.first())

    }

    override suspend fun getById(id : String): Result<Team> {
        val teamEntity =  teamDAO.getTeamById(id)
        if(teamEntity !== null) {
            return Result.success(teamEntity.toDomain())

        }

        return Result.failure(exception = DomainError.NotFound())

    }

   override suspend fun getByName(name : String) : Result<Team> {
        val teamEntity = teamDAO.getTeamByName(name)
        if(teamEntity !== null) {
            return Result.success(teamEntity.toDomain())

        }

       return Result.failure(exception = DomainError.NotFound())
    }


}