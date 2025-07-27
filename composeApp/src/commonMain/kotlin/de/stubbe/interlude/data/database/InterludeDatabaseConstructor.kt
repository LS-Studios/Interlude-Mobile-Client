package de.stubbe.interlude.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object InterludeDatabaseConstructor : RoomDatabaseConstructor<InterludeDatabase> {
    override fun initialize(): InterludeDatabase
}