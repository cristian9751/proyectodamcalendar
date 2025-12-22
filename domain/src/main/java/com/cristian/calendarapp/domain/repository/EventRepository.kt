package com.cristian.calendarapp.domain.repository

import com.cristian.calendarapp.domain.entity.Event
import java.util.Date

interface EventRepository {
    suspend fun save(event : Event) : Result<Event>
    suspend fun findByTeamId(teamId : String) : Result<List<Event>>
}