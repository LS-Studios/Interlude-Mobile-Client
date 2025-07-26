package de.stubbe.interlude.network

import de.stubbe.interlude.config.BuildKonfig
import de.stubbe.interlude.network.dto.ConvertedLinkDto
import de.stubbe.interlude.network.dto.ConvertedLinksDto
import de.stubbe.interlude.network.dto.ProviderDto
import de.stubbe.interlude.network.model.DataError
import de.stubbe.interlude.network.model.Result
import de.stubbe.interlude.ui.theme.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class InterludeSongDataSource(
    private val client: HttpClient
) {

    suspend fun getAvailablePlatforms(): Result<List<ProviderDto>, DataError.Remote> = safeCall<List<ProviderDto>> {
        client.get(
            urlString = "${Constants.BASE_URL}/platforms"
        ) {
            bearerAuth(BuildKonfig.API_TOKEN) //TODO encode to base64
        }
    }

    suspend fun convert(
        link: String
    ): Result<ConvertedLinksDto, DataError.Remote> = safeCall<ConvertedLinksDto> {
        client.get(
            urlString = "${Constants.BASE_URL}/convert"
        ) {
            parameter("link", link)
            bearerAuth(BuildKonfig.API_TOKEN)
        }
    }

}