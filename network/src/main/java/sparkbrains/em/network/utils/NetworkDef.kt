package sparkbrains.em.network.utils

import androidx.annotation.StringDef

class NetworkDef {

    @Retention(AnnotationRetention.SOURCE)
    @Target(AnnotationTarget.TYPE)
    @StringDef(
        REQUEST_TIMED_OUT,
        SOCKET_TIMED_OUT,
        CONNECTION_TIMED_OUT,
        NO_INTERNET,
        UNRESOLVED_SERVER_ADDRESS,
        FAILED_TO_CONNECT
    )
    annotation class Type

    companion object {
        const val REQUEST_TIMED_OUT = "Request timed out"
        const val SOCKET_TIMED_OUT = "Socket timed out"
        const val CONNECTION_TIMED_OUT = "Connection timed out"
        const val NO_INTERNET = "No internet or something went wrong !"
        const val UNRESOLVED_SERVER_ADDRESS = "Unresolved server address"
        const val FAILED_TO_CONNECT = "Failed to connect to server"
        const val API_ERROR = "API returned error"
        const val SOMETHING_WENT_WRONG = "Something went wrong. Please try again later"

    }
}