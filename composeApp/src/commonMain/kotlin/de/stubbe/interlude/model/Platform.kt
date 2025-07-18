package de.stubbe.interlude.model

import org.jetbrains.compose.resources.DrawableResource

data class Platform(
    val uid: String,
    val url: String,
    val name: String,
    val iconUrl: String,
    val fallbackResource: DrawableResource
)