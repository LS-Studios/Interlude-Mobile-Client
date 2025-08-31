package de.stubbe.interlude.domain.repository

import de.stubbe.interlude.data.database.entities.CachedProviderEntity
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
    private val cachedProviderRepository: CachedProviderRepository,
) {
    private suspend fun fetchAndCacheFromNetwork(): Result<List<Provider>, DataError> {
        val result = songDataSource.getAvailableProviders()

        result.onSuccess { dtos ->
            val entities = dtos.map { CachedProviderEntity.fromDto(it) }
            cachedProviderRepository.setCachedProviders(entities)
        }

        return result.map { dtos -> dtos.map { Provider.fromDto(it) } }
    }

    suspend fun getAvailableProviders(): Result<List<Provider>, DataError> {
        val cached = cachedProviderRepository.getCachedProviders()

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
    ): Result<List<ConvertedLink>, DataError> = getAvailableProviders()
        .flatMap { providers ->
            songDataSource.convert(link).map { dto ->
                dto.results.mapNotNull { dto -> ConvertedLink.fromDto(providers, dto) }
            }
        }
}