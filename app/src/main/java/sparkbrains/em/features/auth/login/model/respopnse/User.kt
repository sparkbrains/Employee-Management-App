package sparkbrains.em.features.auth.login.model.respopnse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("email")
    var email: String? = null,
    @SerialName("exp")
    var exp: String? = null,
    @SerialName("orig_iat")
    var origIat: Int? = null,
    @SerialName("permissions")
    var permissions: List<String?>? = null,
    @SerialName("user_id")
    var userId: String? = null,
    @SerialName("username")
    var username: String? = null
)