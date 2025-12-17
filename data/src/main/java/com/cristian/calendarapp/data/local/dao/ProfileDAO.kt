package com.cristian.calendarapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.cristian.calendarapp.data.local.entities.ProfileEntity
import com.cristian.calendarapp.domain.ROLE
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDAO {

    @Upsert
    suspend fun  updateProfile(profile : ProfileEntity)

    @Delete
    suspend fun deleteProfile(profile : ProfileEntity)

    @Query("SELECT * FROM profile LIMIT 1")
     fun getProfile() : Flow<ProfileEntity>


    @Query("SELECT role FROM profile LIMIT 1")
    suspend fun getRole() : ROLE


}