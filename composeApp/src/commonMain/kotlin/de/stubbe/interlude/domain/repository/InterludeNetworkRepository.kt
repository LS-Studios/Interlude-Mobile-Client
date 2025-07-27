package de.stubbe.interlude.domain.repository

import de.stubbe.interlude.data.database.entities.CachedPlatformEntity
import de.stubbe.interlude.data.network.InterludeSongDataSource
import de.stubbe.interlude.data.network.model.DataError
import de.stubbe.interlude.data.network.model.map
import de.stubbe.interlude.data.network.model.onSuccess
import de.stubbe.interlude.domain.model.ConvertedLink
import de.stubbe.interlude.domain.model.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import de.stubbe.interlude.data.network.model.Result
import de.stubbe.interlude.data.network.model.flatMap

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