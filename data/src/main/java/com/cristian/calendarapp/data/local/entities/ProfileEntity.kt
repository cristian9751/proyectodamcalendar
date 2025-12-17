package com.cristian.calendarapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cristian.calendarapp.domain.ROLE
import com.cristian.calendarapp.domain.entity.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey
    @SerialName("user_id")
    var userId : String,

    var firstname : String,

    var lastname : String,

    var email : String,

    var role : ROLE,
    @Transient
     var isSynchronized: Boolean = false
)

fun ProfileEntity.toDomain() : User {
    return User(
        id = this.userId,
        firstname = this.firstname,
        lastname = this.lastname,
        email = this.email,
        role = this.role
    )
}