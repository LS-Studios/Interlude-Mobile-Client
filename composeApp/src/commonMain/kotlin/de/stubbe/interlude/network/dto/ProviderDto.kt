package de.stubbe.interlude.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProviderDto(
    val name: String,
    val url: String,
    val iconUrl: String,
)
