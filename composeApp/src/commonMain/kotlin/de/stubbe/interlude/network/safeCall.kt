package de.stubbe.interlude.network

import de.stubbe.interlude.network.model.DataError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext
import de.stubbe.interlude.network.model.Result

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError> {
    val response = try {
        execute()
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(DataError(e.message ?: ""))
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                Result.Error(DataError("Error while serializing data"))
            }
        }
        408 -> Result.Error(DataError("Request timeout"))
        429 -> Result.Error(DataError("Too many requests"))
        in 500..599 -> Result.Error(DataError("Internal server error"))
        else -> Result.Error(DataError("Unknown error"))
    }
}