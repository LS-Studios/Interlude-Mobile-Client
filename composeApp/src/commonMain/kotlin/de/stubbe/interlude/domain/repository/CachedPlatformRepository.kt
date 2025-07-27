package de.stubbe.interlude.domain.repository

import de.stubbe.interlude.data.database.dao.CachedPlatformDao
import de.stubbe.interlude.data.database.entities.CachedPlatformEntity

class CachedPlatformRepository(
    private val cachedPlatformDao: CachedPlatformDao
) {

    suspend fun getCachedPlatforms(): List<CachedPlatformEntity> = cachedPlatformDao.getCachedPlatforms()

    suspend fun setCachedPlatforms(cachedPlatforms: List<CachedPlatformEntity>) {
        cachedPlatformDao.clearAll()
        cachedPlatformDao.insertAll(cachedPlatforms)
    }

}