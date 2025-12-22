package com.cristian.calendarapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cristian.calendarapp.data.local.dao.EventDAO
import com.cristian.calendarapp.data.local.dao.ProfileDAO
import com.cristian.calendarapp.data.local.dao.TeamDAO
import com.cristian.calendarapp.data.local.entities.EventEntity
import com.cristian.calendarapp.data.local.entities.ProfileEntity
import com.cristian.calendarapp.data.local.entities.TeamEntity

@Database(
    entities = [EventEntity::class, TeamEntity::class, ProfileEntity::class],
    version = 7
)
@TypeConverters(Converters::class)
abstract class LocalDatabase  : RoomDatabase(){
    abstract val eventDao : EventDAO
    abstract val teamDao : TeamDAO
    abstract val profileDao : ProfileDAO

}