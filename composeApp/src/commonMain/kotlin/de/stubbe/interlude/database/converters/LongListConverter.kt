package de.stubbe.interlude.database.converters

import androidx.room.TypeConverter

public class LongListConverter {

    @TypeConverter
    public fun fromList(list: List<Long>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    public fun toList(string: String): List<Long> {
        return string.split(",").map { it.toLong() }
    }

}