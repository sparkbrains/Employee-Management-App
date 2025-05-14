package sparkbrains.em.features.auth.login.model.state

data class LogInUiState(
    val email: String = "",
    val password: String = "",

    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,

    val emailError: String? = null,
    val passwordError: String? = null
)