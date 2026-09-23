package edu.ucb.project.signin.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.project.signin.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInVM(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInState())
    val uiState: StateFlow<SignInState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<SignInEffects>()
    val uiEffect: SharedFlow<SignInEffects> = _uiEffect.asSharedFlow()

    fun onEvent(event: SignInEvents) {
        when (event) {
            is SignInEvents.OnEmailChanged -> _uiState.update { it.copy(email = event.email) }
            is SignInEvents.OnPasswordChanged -> _uiState.update { it.copy(pass = event.pass) }
            is SignInEvents.OnSubmit -> performLogin()
        }
    }

    private fun performLogin() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                loginUseCase(_uiState.value.email, _uiState.value.pass)
                _uiState.update { it.copy(isLoading = false) }
                _uiEffect.emit(SignInEffects.NavigateToMovies)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
