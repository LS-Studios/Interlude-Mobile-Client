package de.stubbe.interlude.repository

import de.stubbe.interlude.database.dao.CachedPlatformDao
import de.stubbe.interlude.database.entities.CachedPlatformEntity

class CachedPlatformRepository(
    private val cachedPlatformDao: CachedPlatformDao
) {

    suspend fun getCachedPlatforms(): List<CachedPlatformEntity> = cachedPlatformDao.getCachedPlatforms()

    suspend fun setCachedPlatforms(cachedPlatforms: List<CachedPlatformEntity>) {
        cachedPlatformDao.clearAll()
        cachedPlatformDao.insertAll(cachedPlatforms)
    }

}