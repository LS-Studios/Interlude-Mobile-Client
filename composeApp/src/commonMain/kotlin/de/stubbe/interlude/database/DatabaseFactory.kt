package de.stubbe.interlude.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<InterludeDatabase>
}