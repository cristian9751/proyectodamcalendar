package com.cristian.calendarapp.data.local

import androidx.room.Database
import com.cristian.calendarapp.data.local.dao.EventDAO
import com.cristian.calendarapp.data.local.dao.ProfileDAO
import com.cristian.calendarapp.data.local.dao.TeamDAO
import com.cristian.calendarapp.data.local.entities.EventEntity
import com.cristian.calendarapp.data.local.entities.TeamEntity

@Database(
    entities = [EventEntity::class, TeamEntity::class],
    version = 1
)
abstract class RoomDatabase {
    abstract val eventDao : EventDAO
    abstract val teamDao : TeamDAO
    abstract val profileDao : ProfileDAO

}