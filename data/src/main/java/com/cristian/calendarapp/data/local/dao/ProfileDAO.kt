package com.cristian.calendarapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.cristian.calendarapp.data.local.entities.ProfileEntity

@Dao
interface ProfileDAO {

    @Upsert
    suspend fun  updateProfile(profile : ProfileEntity)

    @Delete
    suspend fun deleteProfile(profile : ProfileEntity)

    @Query("SELECT * FROM profile")
    suspend fun getProfile() : ProfileEntity


}