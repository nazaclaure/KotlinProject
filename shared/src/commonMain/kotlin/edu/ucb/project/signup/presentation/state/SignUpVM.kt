package edu.ucb.project.signup.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.project.signup.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpVM(private val registerUseCase: RegisterUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<SignUpEffects>()
    val uiEffect: SharedFlow<SignUpEffects> = _uiEffect.asSharedFlow()

    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.OnNameChanged -> _uiState.update { it.copy(name = event.name) }
            is SignUpEvents.OnEmailChanged -> _uiState.update { it.copy(email = event.email) }
            is SignUpEvents.OnPasswordChanged -> _uiState.update { it.copy(pass = event.pass) }
            is SignUpEvents.OnRegisterSubmitted -> performRegister()
        }
    }

    private fun performRegister() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                registerUseCase(_uiState.value.name, _uiState.value.email, _uiState.value.pass)
                _uiState.update { it.copy(isLoading = false) }
                _uiEffect.emit(SignUpEffects.NavigateToLogin)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
