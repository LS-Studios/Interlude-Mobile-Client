package de.stubbe.interlude.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "converted_song_history")
data class ConvertSongHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val link: String,
    @ColumnInfo(name = "date_url")
    val coverUrl: String,
    val platform: String
)
