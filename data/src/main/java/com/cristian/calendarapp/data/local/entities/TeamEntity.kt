package com.cristian.calendarapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cristian.calendarapp.domain.entity.Team

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey
    var id : String,
    var name : String,
    var  description : String,
    var isSynchronized : Boolean = false
)


fun TeamEntity.toDomain( ) : Team {
    return Team(
        id = this.id,
        name = this.name,
        description = this.description
    )

}

fun Team.toEntity() : TeamEntity {
    return TeamEntity(
        id = this.id,
        name = this.name,
        description = this.description
    )

}