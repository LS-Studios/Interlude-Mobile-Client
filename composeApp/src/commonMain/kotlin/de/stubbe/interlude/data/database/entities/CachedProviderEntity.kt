package de.stubbe.interlude.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.stubbe.interlude.data.network.dto.ProviderDto

@Entity(tableName = "cached_provider")
data class CachedProviderEntity(
    @PrimaryKey
    val name: String,
    val url: String,
    @ColumnInfo(name = "logo_url")
    val logoUrl: String,
    @ColumnInfo(name = "icon_url")
    val iconUrl: String
) {
    fun toProvider() = ProviderDto(
        name = name,
        url = url,
        logoUrl = logoUrl,
        iconUrl = iconUrl,
    )
    
    companion object Companion {
        fun fromDto(dto: ProviderDto) = CachedProviderEntity(
            name = dto.name,
            url = dto.url,
            logoUrl = dto.logoUrl,
            iconUrl = dto.iconUrl,
        )
    }
}
