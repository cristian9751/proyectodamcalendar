package com.cristian.calendarapp.presentation.model

import com.cristian.calendarapp.domain.entity.User

data class UserModel(
    val id : String,
    val firstname : String,
    val lastname : String
)

fun User.fromDomain() : UserModel {
    return UserModel(
        id = this.id,
        firstname = this.firstname,
        lastname = this.lastname
    )
}