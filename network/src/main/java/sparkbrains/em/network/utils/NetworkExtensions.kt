package sparkbrains.em.network.utils

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header

// Extension for setting headers
fun HttpRequestBuilder.configureHeaders(headers: Map<String, String>?) {
    headers?.forEach { (key, value) -> header(key, value) }
}

// Extension for setting query params
fun HttpRequestBuilder.configureQueryParams(queryParams: Map<String, String>?) {
    url {
        queryParams?.forEach { (key, value) ->
            parameters.append(key, value)
        }
    }
}