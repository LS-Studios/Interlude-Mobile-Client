package de.stubbe.interlude.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConvertedLinkDto(
    val provider: String,
    val type: String,
    val displayName: String,
    val url: String,
    val artwork: String,
) {
    companion object {
        fun fromConvertedLink(convertedLink: de.stubbe.interlude.domain.model.ConvertedLink): ConvertedLinkDto {
            return ConvertedLinkDto(
                provider = convertedLink.provider.name,
                type = convertedLink.type.name,
                displayName = convertedLink.displayName,
                url = convertedLink.url,
                artwork = convertedLink.artwork
            )
        }
    }
}
