package com.cristian.calendarapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey
    var id : String,
    var name : String,
    var  description : String,
    var isSynchronized : Boolean = false
)