package de.stubbe.interlude.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProviderDto(
    val name: String,
    val url: String,
    val logoUrl: String,
    val iconUrl: String,
)
