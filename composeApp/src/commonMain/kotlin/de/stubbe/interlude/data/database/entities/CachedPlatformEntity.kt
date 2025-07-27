package de.stubbe.interlude.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.stubbe.interlude.data.network.dto.ProviderDto

@Entity(tableName = "cached_platform")
data class CachedPlatformEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val url: String,
    @ColumnInfo(name = "icon_url")
    val iconUrl: String
) {
    companion object {
        fun fromDto(dto: ProviderDto) = CachedPlatformEntity(
            name = dto.name,
            url = dto.url,
            iconUrl = dto.iconUrl,
        )
    }
}
