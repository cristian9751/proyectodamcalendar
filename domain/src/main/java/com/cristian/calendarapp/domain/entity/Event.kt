package com.cristian.calendarapp.domain.entity

import java.util.Date

class Event(
    val id : String = "",
    var name : String,
    var type : String,
    var date : Date,
    var description : String
) {
}