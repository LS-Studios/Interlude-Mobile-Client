package de.stubbe.interlude.domain.repository

import de.stubbe.interlude.data.database.dao.CachedProviderDao
import de.stubbe.interlude.data.database.entities.CachedProviderEntity
import kotlinx.coroutines.flow.Flow

class CachedProviderRepository(
    private val cachedProviderDao: CachedProviderDao
) {

    fun getCachedProvidersFlow(): Flow<List<CachedProviderEntity>> = cachedProviderDao.getCachedProvidersFlow()

    suspend fun getCachedProviders(): List<CachedProviderEntity> = cachedProviderDao.getCachedProviders()

    suspend fun setCachedProviders(cachedProviders: List<CachedProviderEntity>) {
        cachedProviderDao.clearAll()
        cachedProviderDao.insertAll(cachedProviders)
    }

}