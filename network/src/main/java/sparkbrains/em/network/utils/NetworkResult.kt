package sparkbrains.em.network.utils

sealed class NetworkResult<out T> {
    data object Idle : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val code: Int, val errorMessage: String) : NetworkResult<Nothing>()
}