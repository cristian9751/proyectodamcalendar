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
    suspend fun insertTeam(team : TeamEntity) : TeamEntity


    @Delete
    suspend fun deleteTeam(team : TeamEntity)


    @Query("SELECT * FROM teams WHERE id = :id")
    fun getTeamById(id : String) : TeamEntity?

    @Query("SELECT * FROM teams WHERE  name = :name")
     fun getTeamByName(name : String) : TeamEntity?

    @Query("SELECT * FROM teams")
     fun getTeams() : Flow<List<TeamEntity>>

    @Query("SELECT * FROM teams WHERE isSynchronized = :synchronized")
    suspend fun getTeamsBySync(synchronized : Boolean) : List<TeamEntity>


}