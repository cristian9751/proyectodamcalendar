package com.cristian.calendarapp.data.local

import androidx.room.TypeConverter
import com.cristian.calendarapp.domain.ROLE
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value : Long?) : Date? {
        return value?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun dateToTimeStamp(date : Date?) : Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun stringToRole(role : String) : ROLE {
        return ROLE.valueOf(role)

    }
}