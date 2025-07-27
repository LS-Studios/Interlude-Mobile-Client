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
)
