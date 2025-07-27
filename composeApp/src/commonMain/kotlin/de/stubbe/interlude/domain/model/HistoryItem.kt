package de.stubbe.interlude.domain.model

data class HistoryItem(
    val id: Long,
    val baseSong: ConvertedLink,
    val convertedLinks: List<ConvertedLink>
)
