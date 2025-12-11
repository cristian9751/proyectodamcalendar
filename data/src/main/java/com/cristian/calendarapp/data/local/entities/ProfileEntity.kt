package com.cristian.calendarapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey
    var userId : String,

    var firstname : String,

    var lastname : String,
)