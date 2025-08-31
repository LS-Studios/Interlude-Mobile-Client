package de.stubbe.interlude.util

import de.stubbe.interlude.config.BuildKonfig
import de.stubbe.interlude.data.network.dto.ConvertedLinkDto
import de.stubbe.interlude.domain.repository.InterludeNetworkRepository
import de.stubbe.interlude.data.network.model.onSuccess
import de.stubbe.interlude.data.network.model.onError
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.base64EncodedStringWithOptions
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding

fun getBase64EncodedApiToken(): String {
    val tokenString = BuildKonfig.API_TOKEN
    val nsString = NSString.create(string = tokenString)
    val data = nsString.dataUsingEncoding(NSUTF8StringEncoding)
    return data?.base64EncodedStringWithOptions(0u) ?: ""
}