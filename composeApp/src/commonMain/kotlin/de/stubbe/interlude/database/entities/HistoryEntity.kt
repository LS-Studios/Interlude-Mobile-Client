package de.stubbe.interlude.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val baseConvertedLinkId: Long,
    val convertedLinkIds: List<Long>,
)
