package de.stubbe.interlude.repository

import de.stubbe.interlude.database.entities.CachedPlatformEntity
import de.stubbe.interlude.model.ConvertedLink
import de.stubbe.interlude.model.Provider
import de.stubbe.interlude.network.InterludeSongDataSource
import de.stubbe.interlude.network.model.DataError
import de.stubbe.interlude.network.model.Result
import de.stubbe.interlude.network.model.flatMap
import de.stubbe.interlude.network.model.map
import de.stubbe.interlude.network.model.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class InterludeNetworkRepository(
    private val songDataSource: InterludeSongDataSource,
    private val cachedPlatformRepository: CachedPlatformRepository,
) {
    private suspend fun fetchAndCacheFromNetwork(): Result<List<Provider>, DataError> {
        val result = songDataSource.getAvailablePlatforms()

        result.onSuccess { dtos ->
            val entities = dtos.map { CachedPlatformEntity.fromDto(it) }
            cachedPlatformRepository.setCachedPlatforms(entities)
        }

        return result.map { dtos -> dtos.map { Provider.fromDto(it) } }
    }

    suspend fun getAvailablePlatforms(): Result<List<Provider>, DataError> {
        val cached = cachedPlatformRepository.getCachedPlatforms()

        if (cached.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                fetchAndCacheFromNetwork()
            }

            return Result.Success(cached.map { Provider.fromCached(it) })
        }

        return fetchAndCacheFromNetwork()
    }

    suspend fun convert(
        link: String,
    ): Result<List<ConvertedLink>, DataError> = getAvailablePlatforms()
        .flatMap { platforms ->
            songDataSource.convert(link).map { dto ->
                dto.results.map { dto -> ConvertedLink.fromDto(platforms, dto) }
            }
        }
}