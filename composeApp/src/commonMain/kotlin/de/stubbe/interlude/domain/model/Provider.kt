package de.stubbe.interlude.domain.model

import de.stubbe.interlude.data.database.entities.CachedProviderEntity
import de.stubbe.interlude.data.network.dto.ProviderDto

data class Provider(
    val name: String,
    val url: String,
    val logoUrl: String,
    val iconUrl: String
) {
    fun getFormattedName(): String = name.split(Regex("(?=[A-Z])")).joinToString(" ")

    companion object Companion {
        fun fromDto(dto: ProviderDto) = Provider(
            name = dto.name,
            url = dto.url,
            logoUrl = dto.logoUrl,
            iconUrl = dto.iconUrl,
        )

        fun fromCached(entity: CachedProviderEntity) = Provider(
            name = entity.name,
            url = entity.url,
            logoUrl = entity.logoUrl,
            iconUrl = entity.iconUrl,
        )
    }
}