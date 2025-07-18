package de.stubbe.interlude.model

data class ConvertedLink(
    val link: String,
    val songName: String,
    val songImageUrl: String,
    val platform: Platform
)
