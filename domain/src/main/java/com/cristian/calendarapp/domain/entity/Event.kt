package com.cristian.calendarapp.domain.entity

import java.util.Date

class Event(
    val id : String = "",
    var name : String,
    var date : Date,
    var description : String,
    var teamId : String
) {
}