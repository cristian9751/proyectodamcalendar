package com.cristian.calendarapp.domain.entity

import com.cristian.calendarapp.domain.ROLE

class User(
    val id : String = "",
    var email : String,
    val password : String = "",
    var firstname : String,
    var lastname : String,
    val role : ROLE,
    val teams : List<Team> = emptyList()
) {

}