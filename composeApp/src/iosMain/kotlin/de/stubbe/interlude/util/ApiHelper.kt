package de.stubbe.interlude.util

import de.stubbe.interlude.config.BuildKonfig
import de.stubbe.interlude.domain.repository.InterludeNetworkRepository
import org.koin.compose.koinInject
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.base64EncodedStringWithOptions
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding

// Function that can be called from Swift to get the base64 encoded API token
fun getBase64EncodedApiToken(): String {
    val tokenString = BuildKonfig.API_TOKEN
    val nsString = NSString.create(string = tokenString)
    val data = nsString.dataUsingEncoding(NSUTF8StringEncoding)
    return data?.base64EncodedStringWithOptions(0u) ?: ""
}