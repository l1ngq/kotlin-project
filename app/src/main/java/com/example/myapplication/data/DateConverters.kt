package com.example.myapplication.data

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverters {

    @TypeConverter
    fun fromDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun toDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }
}
