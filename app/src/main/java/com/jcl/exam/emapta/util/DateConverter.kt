package com.jcl.exam.emapta.util

import androidx.room.TypeConverter

import java.util.Date

public class DateConverter {

    @TypeConverter
    public fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    public fun fromDate(date: Date?): Long? {
        return date?.time
    }
}