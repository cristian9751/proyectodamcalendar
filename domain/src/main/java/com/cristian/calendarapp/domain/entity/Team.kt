package com.cristian.calendarapp.domain.entity

class Team(
    val id : String = "",
    var name : String,
    var description : String,
    val events : List<Event>  = emptyList()
) {

}