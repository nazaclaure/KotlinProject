package edu.ucb.project.profile.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.project.profile.domain.usecase.GetProfileUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileVM(private val getProfileUseCase: GetProfileUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<ProfileEffects>()
    val uiEffect: SharedFlow<ProfileEffects> = _uiEffect.asSharedFlow()

    fun onEvent(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.LoadProfile -> fetchProfile()
            is ProfileEvents.OnLogout -> {
                viewModelScope.launch { _uiEffect.emit(ProfileEffects.NavigateToLogin) }
            }
        }
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val profile = getProfileUseCase()
                _uiState.update { it.copy(profile = profile, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
