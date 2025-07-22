package de.stubbe.interlude.repository

import de.stubbe.interlude.network.dto.ConvertedLinkDto
import de.stubbe.interlude.network.model.DataError
import de.stubbe.interlude.network.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters

class InterludeNetworkRepository(
    val client: HttpClient
) {

    suspend fun convert(
        link: String,
        platform: String
    ): Result<List<ConvertedLinkDto>, DataError.Remote> = safeCall<List<ConvertedLinkDto>> {
        client.get {
            parameters {
                append("link", link)
                append("platform", platform)
            }
            contentType(ContentType.Application.Json)

        }
    }

}