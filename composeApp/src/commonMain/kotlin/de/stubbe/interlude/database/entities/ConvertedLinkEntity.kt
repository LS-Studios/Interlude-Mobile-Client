package de.stubbe.interlude.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.stubbe.interlude.model.ConvertedLink
import de.stubbe.interlude.model.ConvertedLinkType

@Entity(tableName = "converted_link")
data class ConvertedLinkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val provider: String,
    val type: ConvertedLinkType,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    val url: String,
    val artwork: String,
) {

    companion object Companion {

        fun fromConvertedLink(convertedLink: ConvertedLink): ConvertedLinkEntity {
            return ConvertedLinkEntity(
                provider = convertedLink.provider.name,
                type = convertedLink.type,
                displayName = convertedLink.displayName,
                url = convertedLink.url,
                artwork = convertedLink.artwork
            )
        }

    }

}
