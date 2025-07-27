package de.stubbe.interlude.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<InterludeDatabase>
}