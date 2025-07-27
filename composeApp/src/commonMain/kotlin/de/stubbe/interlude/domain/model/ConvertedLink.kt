package de.stubbe.interlude.domain.model

import de.stubbe.interlude.data.database.entities.ConvertedLinkEntity
import de.stubbe.interlude.data.network.dto.ConvertedLinkDto

data class ConvertedLink(
    val provider: Provider,
    val type: ConvertedLinkType,
    val displayName: String,
    val url: String,
    val artwork: String,
) {
    companion object {

        fun fromDto(providers: List<Provider>, dto: ConvertedLinkDto): ConvertedLink {
            return ConvertedLink(
                provider = providers.find { it.name == dto.provider } ?: throw Exception("Provider ${dto.provider} not found"),
                type = ConvertedLinkType.entries.find { it.name.lowercase() == dto.type.lowercase() } ?: throw Exception("Type not found"),
                displayName = dto.displayName,
                url = dto.url,
                artwork = dto.artwork
            )
        }

        fun fromEntity(providers: List<Provider>, entity: ConvertedLinkEntity): ConvertedLink {
            return ConvertedLink(
                provider = providers.find { it.name == entity.provider } ?: throw Exception("Platform not found"),
                type = ConvertedLinkType.entries.find { it.name.lowercase() == entity.type.name.lowercase() } ?: throw Exception("Type not found"),
                displayName = entity.displayName,
                url = entity.url,
                artwork = entity.artwork
            )
        }

    }
}
