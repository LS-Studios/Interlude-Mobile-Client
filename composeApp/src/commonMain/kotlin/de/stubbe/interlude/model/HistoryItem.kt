package de.stubbe.interlude.model

import de.stubbe.interlude.database.entities.HistoryEntity

data class HistoryItem(
    val id: Long,
    val baseSong: ConvertedLink,
    val convertedLinks: List<ConvertedLink>
)
