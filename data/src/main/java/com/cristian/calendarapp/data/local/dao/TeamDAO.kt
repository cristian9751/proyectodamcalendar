package com.cristian.calendarapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.cristian.calendarapp.data.local.entities.EventEntity
import com.cristian.calendarapp.data.local.entities.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TeamDAO {

    @Upsert()
    abstract suspend fun insertTeam(team : TeamEntity)

    @Insert
    abstract suspend fun insertEvents(events : List<EventEntity>)


    @Delete
    abstract suspend fun deleteTeam(team : TeamEntity)


    @Query("SELECT * FROM teams WHERE id = :id")
    abstract suspend fun getTeamById(id : String) : TeamEntity?

    @Query("SELECT * FROM teams WHERE  name = :name")
    abstract  suspend fun getTeamByName(name : String) : TeamEntity?

    @Query("SELECT * FROM teams")
     abstract fun getTeams() : Flow<List<TeamEntity>>

    @Query("SELECT * FROM teams WHERE isSynchronized = :synchronized")
    abstract suspend fun getTeamsBySync(synchronized : Boolean) : List<TeamEntity>


    suspend fun insertTeamWithEvents(team : TeamEntity) {
        val events : List<EventEntity> = team.getEvents()
        events.forEach { event ->
            event.teamId = team.id
        }

        insertEvents(events)
        insertTeam(team)


    }

    @Query("SELECT * FROM events WHERE teamId = :teamId")
    abstract suspend fun getEventsByTeam(teamId : String) : List<EventEntity>




    suspend fun getTeamWithEvents(teamId : String)  : TeamEntity? {
        val team = getTeamById(teamId)
       if(team !== null) {
           val events = getEventsByTeam(teamId)
           team.setEvents(events)

       }

        return team

    }
}