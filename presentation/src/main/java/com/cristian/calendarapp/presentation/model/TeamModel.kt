package com.cristian.calendarapp.presentation.model

import com.cristian.calendarapp.domain.entity.Team


data class TeamModel(
    val id : String,
    val name : String,
    val description : String,
    val eventCount : Int = 0

)


fun TeamModel.toDomain() : Team {
    return Team(
        id = id,
        name = name,
        description = description)

}

fun Team.toModel() : TeamModel {
    return TeamModel(
        id = this.id,
        name = this.name,
        description = this.description,
        eventCount = this.events.size
    )
}