package com.imquarantined.db

import androidx.room.TypeConverter
import java.util.*

/* Created by ashiq.buet16 **/

class DateConverter() {

    @TypeConverter
    fun toTimeStamp(date: Date?): Long {
        return date?.time!!
    }

    @TypeConverter
    fun toDate(timeStamp: Long?): Date? {
        return timeStamp?.let { Date(it) }
    }
}