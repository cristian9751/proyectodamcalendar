package com.cristian.calendarapp.presentation.model

import com.cristian.calendarapp.domain.entity.Event
import java.util.Calendar
import java.util.UUID

class CalendarEvent(
    val id : String = UUID.randomUUID().toString(),
    val title : String,
    val description : String,
    val teamId : String,
    val day : Int
)

fun Event.toCalendarEvent() : CalendarEvent {
    return CalendarEvent(
        title = this.name,
        description = this.description,
        day = this.date.day,
        id = this.id,
        teamId = this.teamId
    )
}

fun CalendarEvent.toDomain()  : Event{
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, this.day)
    return Event(
        id = this.id,
        name = this.title,
        description = this.description,
        date = calendar.time,
        teamId = this.teamId
    )
}