package de.stubbe.interlude.database.dao

import androidx.room.Dao
import androidx.room.Query
import de.stubbe.interlude.database.entities.CachedPlatformEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CachedPlatformDao: BaseDao<CachedPlatformEntity> {

    @Query("SELECT * FROM cached_platform")
    suspend fun getCachedPlatforms(): List<CachedPlatformEntity>

    @Query("DELETE FROM cached_platform")
    suspend fun clearAll()

}