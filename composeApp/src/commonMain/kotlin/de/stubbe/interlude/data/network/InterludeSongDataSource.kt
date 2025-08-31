package de.stubbe.interlude.data.network

import de.stubbe.interlude.data.network.dto.ConvertedLinksDto
import de.stubbe.interlude.data.network.dto.ProviderDto
import de.stubbe.interlude.data.network.model.DataError
import de.stubbe.interlude.data.network.model.Result
import de.stubbe.interlude.platform.ui.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.encodeURLParameter

class InterludeSongDataSource(
    private val client: HttpClient
) {

    suspend fun getAvailableProviders(): Result<List<ProviderDto>, DataError> = safeCall<List<ProviderDto>> {
        client.get(
            urlString = "${Constants.BASE_URL}/providers"
        )
    }

    suspend fun convert(
        link: String
    ): Result<ConvertedLinksDto, DataError> = safeCall<ConvertedLinksDto> {
        client.get(
            urlString = "${Constants.BASE_URL}/convert"
        ) {
            parameter("link", link.encodeURLParameter())
        }
    }

}