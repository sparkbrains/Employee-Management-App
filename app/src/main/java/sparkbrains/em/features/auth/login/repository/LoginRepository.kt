package sparkbrains.em.features.auth.login.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import sparkbrains.em.features.auth.login.model.request.LoginRequest
import sparkbrains.em.features.auth.login.model.respopnse.LoginResponse
import sparkbrains.em.network.client.KtorClient
import sparkbrains.em.network.utils.NetworkResult

class LoginRepository(
    private val ktorClient: KtorClient,
) {

    fun login(email: String, password: String) =
        flow<NetworkResult<LoginResponse>> {
            emit(
                ktorClient.post<LoginResponse, LoginRequest>(
                    endpoint = "accounts/login/", body = LoginRequest(
                        email = email, password = password
                    )
                )
            )
        }.onStart {
            emit(NetworkResult.Loading)
        }.flowOn(Dispatchers.IO)
}