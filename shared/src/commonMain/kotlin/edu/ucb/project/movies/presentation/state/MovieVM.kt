package edu.ucb.project.movies.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.project.movies.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieVM(private val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieState())
    val uiState: StateFlow<MovieState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<MovieEffects>()
    val uiEffect: SharedFlow<MovieEffects> = _uiEffect.asSharedFlow()

    fun onEvent(event: MovieEvents) {
        when (event) {
            is MovieEvents.LoadMovies -> fetchMovies()
            is MovieEvents.OnMovieClicked -> {
                viewModelScope.launch {
                    _uiEffect.emit(MovieEffects.NavigateToDetail(event.id))
                }
            }
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val list = getMoviesUseCase()
                _uiState.update { it.copy(movies = list, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
