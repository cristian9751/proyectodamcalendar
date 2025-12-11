package com.cristian.calendarapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.cristian.calendarapp.data.local.entities.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDAO {

    @Upsert()
    suspend fun insertTeam(team : TeamEntity)

    @Delete
    suspend fun deleteTeam(team : TeamEntity)

    @Query("SELECT * FROM teams WHERE  name LIKE :name")
     fun getTeamByName(name : String) : LiveData<List<TeamEntity>>

    @Query("SELECT * FROM teams")
     fun getAllTeams() : LiveData<List<TeamEntity>>

    @Query("SELECT * FROM teams WHERE isSynchronized = :synchronized")
    suspend fun getTeamsBySync(synchronized : Boolean) : List<TeamEntity>

}