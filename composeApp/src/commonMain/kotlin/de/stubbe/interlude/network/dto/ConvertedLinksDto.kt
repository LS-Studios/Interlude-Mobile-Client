package de.stubbe.interlude.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConvertedLinksDto(
    val results: List<ConvertedLinkDto>
)
