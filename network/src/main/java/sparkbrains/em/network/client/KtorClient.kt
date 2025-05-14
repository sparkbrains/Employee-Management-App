package sparkbrains.em.network.client

import sparkbrains.em.network.utils.NetworkDef
import sparkbrains.em.network.utils.NetworkResult
import sparkbrains.em.network.utils.configureHeaders
import sparkbrains.em.network.utils.configureQueryParams
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.TimeoutCancellationException
import java.net.ConnectException
import java.net.UnknownHostException

class KtorClient(val httpClient: HttpClient) {

    suspend inline fun <reified T> get(
        endpoint: String,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): NetworkResult<T> = safeRequest {
        httpClient.get(endpoint) {
            configureHeaders(headers)
            configureQueryParams(parameters)
        }.body<T>()
    }

    suspend inline fun <reified T, reified R> post(
        endpoint: String,
        body: R? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): NetworkResult<T> = safeRequest {
        httpClient.post(endpoint) {
            configureHeaders(headers)
            configureQueryParams(parameters)
            body?.let {
                setBody(body)
            }
        }.body<T>()
    }

    suspend inline fun <reified T, reified R> put(
        endpoint: String,
        body: R? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): NetworkResult<T> = safeRequest {
        httpClient.put(endpoint) {
            configureHeaders(headers)
            configureQueryParams(parameters)
            body?.let {
                setBody(body)
            }
        }.body<T>()
    }

    suspend inline fun <reified T, reified R> patch(
        endpoint: String,
        body: R? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): NetworkResult<T> = safeRequest {
        httpClient.patch(endpoint) {
            configureHeaders(headers)
            configureQueryParams(parameters)
            body?.let {
                setBody(body)
            }
        }.body<T>()
    }

    suspend inline fun <reified T> delete(
        endpoint: String,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): NetworkResult<T> = safeRequest {
        httpClient.delete(endpoint) {
            configureHeaders(headers)
            configureQueryParams(parameters)
        }.body<T>()
    }

    suspend inline fun <reified T> safeRequest(
        crossinline block: suspend () -> T
    ): NetworkResult<T> {
        return try {
            NetworkResult.Success(block())
        } catch (e: RedirectResponseException) {
            // 3xx responses
            NetworkResult.Error(e.response.status.value, e.message)
        } catch (e: ClientRequestException) {
            // 4xx responses
            NetworkResult.Error(e.response.status.value, e.message)
        } catch (e: ServerResponseException) {
            // 5xx responses
            NetworkResult.Error(e.response.status.value, e.message)
        } catch (e: TimeoutCancellationException) {
            NetworkResult.Error(
                HttpStatusCode.RequestTimeout.value,
                "${NetworkDef.REQUEST_TIMED_OUT}: ${e.message ?: e.localizedMessage}"
            )
        } catch (e: SocketTimeoutException) {
            NetworkResult.Error(
                HttpStatusCode.RequestTimeout.value,
                "${NetworkDef.SOCKET_TIMED_OUT}: ${e.message ?: e.localizedMessage}"
            )
        } catch (e: ConnectTimeoutException) {
            NetworkResult.Error(
                HttpStatusCode.GatewayTimeout.value,
                "${NetworkDef.CONNECTION_TIMED_OUT}: ${e.message ?: e.localizedMessage}"
            )
        } catch (e: UnknownHostException) {
            NetworkResult.Error(
                HttpStatusCode.BadGateway.value,
                "${NetworkDef.NO_INTERNET}: ${e.message ?: e.localizedMessage}"
            )
        } catch (e: UnresolvedAddressException) {
            NetworkResult.Error(
                HttpStatusCode.ServiceUnavailable.value,
                "${NetworkDef.UNRESOLVED_SERVER_ADDRESS}: ${e.message ?: e.localizedMessage}"
            )
        } catch (e: ConnectException) {
            NetworkResult.Error(
                HttpStatusCode.ServiceUnavailable.value,
                "${NetworkDef.FAILED_TO_CONNECT}: ${e.message ?: e.localizedMessage}"
            )
        } catch (e: ResponseException) {
            // Fallback for any unhandled response errors
            NetworkResult.Error(
                e.response.status.value,
                "${NetworkDef.API_ERROR}: ${e.message ?: e.localizedMessage}"
            )
        } catch (e: Exception) {
            NetworkResult.Error(
                HttpStatusCode.InternalServerError.value,
                "${NetworkDef.SOMETHING_WENT_WRONG}: ${e.message ?: e.localizedMessage}"
            )
        }
    }
}