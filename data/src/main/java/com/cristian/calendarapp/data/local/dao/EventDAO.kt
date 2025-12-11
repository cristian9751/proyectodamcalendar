package com.cristian.calendarapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.cristian.calendarapp.data.local.entities.EventEntity
import kotlinx.coroutines.flow.Flow
import java.sql.Date

@Dao
interface EventDAO  {

    @Upsert
    suspend fun insertEvent(event : EventEntity)

    @Delete
    suspend fun deleteEvent(event : EventEntity)

    @Query("SELECT * FROM events WHERE title LIKE :name")
     fun findEventByName(name : String) : LiveData<List<EventEntity>>

    @Query("SELECT * FROM events WHERE date  = :date")
     fun findEventByDate(date: Date) : LiveData<List<EventEntity>>

    @Query("SELECT * FROM events")
     fun getAllEvents() : LiveData<List<EventEntity>>
    @Query("SELECT * FROM events WHERE isSynchronized = :synchronized")
    suspend fun getEventsBySync(synchronized : Boolean) : List<EventEntity>

    @Query("SELECT * FROM events WHERE teamId = :teamId ")
     fun getEventsByTeam(teamId : String) : Flow<List<EventEntity>>
}