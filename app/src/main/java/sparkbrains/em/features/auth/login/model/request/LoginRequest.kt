package sparkbrains.em.features.auth.login.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("email")
    var email: String? = null,
    @SerialName("password")
    var password: String? = null
)