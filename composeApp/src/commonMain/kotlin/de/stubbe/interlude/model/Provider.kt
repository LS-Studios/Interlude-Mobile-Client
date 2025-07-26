package de.stubbe.interlude.model

import de.stubbe.interlude.database.entities.CachedPlatformEntity
import de.stubbe.interlude.network.dto.ProviderDto

data class Provider(
    val name: String,
    val url: String,
    val iconUrl: String
) {
    fun getFormattedName(): String = name.split(Regex("(?=[A-Z])")).joinToString(" ")

    companion object Companion {
        fun fromDto(dto: ProviderDto) = Provider(
            name = dto.name,
            url = dto.url,
            iconUrl = dto.iconUrl,
        )

        fun fromCached(entity: CachedPlatformEntity) = Provider(
            name = entity.name,
            url = entity.url,
            iconUrl = entity.iconUrl,
        )
    }
}