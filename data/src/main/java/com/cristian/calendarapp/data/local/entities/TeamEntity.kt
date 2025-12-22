package com.cristian.calendarapp.data.local.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.cristian.calendarapp.domain.entity.Team
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey
    var id : String,
    var name : String,
    var  description : String,

    )  {
    @Transient
    var isSynchronized : Boolean = false
    @Ignore
    @SerialName("event")
     var events : List<EventEntity> = emptyList()



    companion object{
        fun toDomainList(teams : List<TeamEntity>) {
            teams.map { team ->
                team.toDomain()
            }
        }
    }
}


fun TeamEntity.toDomain( ) : Team {
    return Team(
        id = this.id,
        name = this.name,
        description = this.description,
        events = this.events.map { event ->
            event.toDomain()
        }
    )

}

fun Team.toLocalEntity() : TeamEntity {
    val events = this.events.map { event ->
        event.toLocalEntity()

    }
    val teamEntity = TeamEntity(
        id = this.id,
        name = this.name,
        description = this.description,

    )
    teamEntity.events = events
    return teamEntity
}