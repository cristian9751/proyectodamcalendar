@file:Suppress("SERIALIZER_TYPE_INCOMPATIBLE")

package com.cristian.calendarapp.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.cristian.calendarapp.data.DateSerializer
import com.cristian.calendarapp.domain.entity.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.Date






@Serializable
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

data class EventEntity constructor(
    @PrimaryKey
    val id: String,
    @SerialName("name")
    val title: String,
    @SerialName("team_id")
    var teamId : String,
    val description: String,
    @Serializable(with = DateSerializer::class)
    val date : Date,
    @Transient
    var isSynchronized : Boolean = false
) {
    companion object {
        fun toDomainList(events : List<EventEntity>) : List<Event> {
            return events.map { event ->
                event.toDomain()
            }
        }
    }
}


fun EventEntity.toDomain() : Event{
    return Event(
        id = this.id,
        name = this.title,
        description = this.description,
        date = this.date,
        teamId = this.teamId
    )
}

fun Event.toLocalEntity() : EventEntity {
    return EventEntity(
        id = this.id,
        title = this.name,
        description = this.description,
        date = this.date,
        teamId = this.teamId
    )
}