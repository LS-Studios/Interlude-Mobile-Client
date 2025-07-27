package de.stubbe.interlude.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import de.stubbe.interlude.data.database.entities.CachedPlatformEntity

@Dao
interface CachedPlatformDao: BaseDao<CachedPlatformEntity> {

    @Query("SELECT * FROM cached_platform")
    suspend fun getCachedPlatforms(): List<CachedPlatformEntity>

    @Query("DELETE FROM cached_platform")
    suspend fun clearAll()

}