package de.stubbe.interlude.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import de.stubbe.interlude.data.database.entities.CachedProviderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CachedProviderDao: BaseDao<CachedProviderEntity> {

    @Query("SELECT * FROM cached_provider")
    fun getCachedProvidersFlow(): Flow<List<CachedProviderEntity>>

    @Query("SELECT * FROM cached_provider")
    suspend fun getCachedProviders(): List<CachedProviderEntity>

    @Query("DELETE FROM cached_provider")
    suspend fun clearAll()

}