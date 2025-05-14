package sparkbrains.em.features.auth.login.model.respopnse


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("access")
    var access: String? = null,
    @SerialName("designation")
    var designation: String? = null,
    @SerialName("refresh")
    var refresh: String? = null,
    @SerialName("role")
    var role: String? = null,
    @SerialName("success")
    var success: Boolean? = null,
    @SerialName("user")
    var user: User? = null
)