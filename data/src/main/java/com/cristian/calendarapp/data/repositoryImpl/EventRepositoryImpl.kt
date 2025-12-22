package com.cristian.calendarapp.data.repositoryImpl

import com.cristian.calendarapp.data.local.dao.EventDAO
import com.cristian.calendarapp.data.local.dao.TeamDAO
import com.cristian.calendarapp.data.local.entities.EventEntity
import com.cristian.calendarapp.data.local.entities.toLocalEntity
import com.cristian.calendarapp.domain.DomainError
import com.cristian.calendarapp.domain.entity.Event
import com.cristian.calendarapp.domain.repository.EventRepository
import java.util.Date
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val teamDAO: TeamDAO,
    private val eventDAO: EventDAO
) : EventRepository {
    override suspend fun save(event: Event): Result<Event> {
        try {
            eventDAO.insertEvent(event.toLocalEntity())

            return Result.success(event)

        } catch (e : Exception) {
            return Result.failure(exception = DomainError.Unexpected())
        }
    }

    override suspend fun findByTeamId(teamId: String): Result<List<Event>> {
        var result : List<Event>  = emptyList()
      try {
          val team = teamDAO.getTeamWithEvents(teamId)
          team?.let { team ->
              result = EventEntity.toDomainList(team.events)
          }
          return Result.success(result)
      } catch (e : Exception) {
          return Result.failure(exception = DomainError.Unexpected())
      }





    }
}