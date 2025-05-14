package sparkbrains.em.features.auth.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sparkbrains.em.features.auth.login.model.respopnse.LoginResponse
import sparkbrains.em.features.auth.login.model.state.LogInUiState
import sparkbrains.em.features.auth.login.repository.LoginRepository
import sparkbrains.em.network.utils.NetworkResult

class LoginViewModel(val loginRepository: LoginRepository) : ViewModel() {

    private val _state = MutableStateFlow(LogInUiState())
    val state = _state.asStateFlow()

    private val _loginResponse = MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Idle)
    val loginResponse = _loginResponse.asStateFlow()

    init {
        state.distinctUntilChangedBy { it.email }
            .map { it.email.isNotEmpty() }
            .onEach { isEmailValid ->
                _state.update {
                    it.copy(
                        isEmailValid = isEmailValid,
                    )
                }
            }
            .launchIn(viewModelScope)

        state.distinctUntilChangedBy { it.password }
            .map { it.password.isNotEmpty() }
            .onEach { isPasswordValid ->
                _state.update {
                    it.copy(
                        isPasswordValid = isPasswordValid,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onEmailChange(email: String) {
        val emailError = if (email.isEmpty()) "Please enter you email." else null
        _state.update { it.copy(email = email, emailError = emailError) }
    }

    fun onPasswordChange(password: String) {
        val passwordError = if (password.isEmpty()) "Please enter your password" else null
        _state.update { it.copy(password = password, passwordError = passwordError) }
    }

    fun validateAndSubmit() {
        val current = _state.value

        val emailError = if (current.email.isEmpty()) "Please enter you email." else null
        val passwordError = if (current.password.isEmpty()) "Please enter your password" else null

        val isFormValid = listOf(
            current.emailError,
            current.passwordError,
        ).all { it == null }

        _state.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        if (isFormValid) {
            login(email = current.email, password = current.password)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(email, password).collect { response ->
                _loginResponse.value = response
            }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}