package de.stubbe.interlude.database.converters

import androidx.room.TypeConverter
import de.stubbe.interlude.model.ConvertedLinkType

public class ConvertedLinkTypeConverter {

    @TypeConverter
    public fun fromConvertedLinkType(type: ConvertedLinkType): String {
        return type.name.lowercase()
    }

    @TypeConverter
    public fun toConvertedLinkType(string: String): ConvertedLinkType {
        return ConvertedLinkType.entries.find { it.name.lowercase() == string } ?: throw Exception("Type not found")
    }

}