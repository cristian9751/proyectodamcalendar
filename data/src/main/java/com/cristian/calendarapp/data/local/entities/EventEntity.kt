package com.cristian.calendarapp.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.cristian.calendarapp.domain.entity.Event
import java.util.Date

@Entity(
    tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["teamId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    var teamId : String,
    val type: String,
    val description: String,
    val date : Date,
    val isSynchronized : Boolean = false
)


fun EventEntity.toDomain() : Event{
    return Event(
        id = this.id,
        name = this.title,
        description = this.description,
        type = this.type,
        date = this.date,
        teamId = this.teamId
    )
}

fun Event.toLocalEntity() : EventEntity {
    return EventEntity(
        id = this.id,
        title = this.name,
        description = this.description,
        type = this.type,
        date = this.date,
        teamId = this.teamId
    )
}