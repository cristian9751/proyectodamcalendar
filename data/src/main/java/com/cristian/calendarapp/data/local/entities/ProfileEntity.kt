package com.cristian.calendarapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cristian.calendarapp.domain.ROLE
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey
    var user_id : String,

    var firstname : String,

    var lastname : String,

    var role : ROLE,
    @Transient
     var isSynchronized: Boolean = false
)