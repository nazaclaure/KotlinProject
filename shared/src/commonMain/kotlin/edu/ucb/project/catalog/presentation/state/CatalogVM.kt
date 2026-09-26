package edu.ucb.project.catalog.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.project.catalog.domain.usecase.GetCatalogUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogVM(private val getCatalogUseCase: GetCatalogUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(CatalogState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<CatalogEffects>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onEvent(event: CatalogEvents) {
        if (event is CatalogEvents.LoadCatalog) {
            loadMovies()
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getCatalogUseCase().fold(
                onSuccess = { movies ->
                    _uiState.update { it.copy(isLoading = false, movies = movies) }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                    _uiEffect.emit(CatalogEffects.ShowError(e.message ?: "Error desconocido"))
                }
            )
        }
    }
}