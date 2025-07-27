package de.stubbe.interlude.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.stubbe.interlude.data.database.converters.ConvertedLinkTypeConverter
import de.stubbe.interlude.data.database.converters.LongListConverter
import de.stubbe.interlude.data.database.dao.CachedPlatformDao
import de.stubbe.interlude.data.database.dao.ConvertedLinkDao
import de.stubbe.interlude.data.database.dao.HistoryDao
import de.stubbe.interlude.data.database.entities.CachedPlatformEntity
import de.stubbe.interlude.data.database.entities.ConvertedLinkEntity
import de.stubbe.interlude.data.database.entities.HistoryEntity

@Database(
    entities = [
        CachedPlatformEntity::class,
        ConvertedLinkEntity::class,
        HistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    LongListConverter::class,
    ConvertedLinkTypeConverter::class
)
@ConstructedBy(InterludeDatabaseConstructor::class)
abstract class InterludeDatabase: RoomDatabase() {

    abstract val convertedLinkDao: ConvertedLinkDao
    abstract val cachedPlatformDao: CachedPlatformDao
    abstract val historyDao: HistoryDao

    companion object Companion {
        const val DB_NAME = "interlude_db.db"
    }

}