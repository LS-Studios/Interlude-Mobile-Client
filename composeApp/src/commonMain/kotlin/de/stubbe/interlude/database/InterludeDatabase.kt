package de.stubbe.interlude.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
@ConstructedBy(InterludeDatabaseConstructor::class)
abstract class InterludeDatabase: RoomDatabase() {

    companion object Companion {
        const val DB_NAME = "interlude_db.db"
    }

}