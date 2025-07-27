package de.stubbe.interlude.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConvertedLinksDto(
    val results: List<ConvertedLinkDto>
)
