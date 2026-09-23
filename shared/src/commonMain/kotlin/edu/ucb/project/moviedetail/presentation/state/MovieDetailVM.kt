package edu.ucb.project.moviedetail.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.project.moviedetail.domain.usecase.GetMovieDetailUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailVM(private val getMovieDetailUseCase: GetMovieDetailUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailState())
    val uiState: StateFlow<MovieDetailState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<MovieDetailEffects>()
    val uiEffect: SharedFlow<MovieDetailEffects> = _uiEffect.asSharedFlow()

    fun onEvent(event: MovieDetailEvents) {
        when (event) {
            is MovieDetailEvents.LoadDetail -> fetchDetail(event.id)
            is MovieDetailEvents.OnBackClicked -> {
                viewModelScope.launch { _uiEffect.emit(MovieDetailEffects.NavigateBack) }
            }
        }
    }

    private fun fetchDetail(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val res = getMovieDetailUseCase(id)
                _uiState.update { it.copy(detail = res, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
