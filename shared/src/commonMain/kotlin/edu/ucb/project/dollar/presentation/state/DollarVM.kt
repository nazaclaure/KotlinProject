package edu.ucb.project.dollar.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.project.dollar.domain.model.DollarModel
import edu.ucb.project.dollar.domain.usecase.GetDollarListUseCase
import edu.ucb.project.dollar.domain.usecase.InsertDollarUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DollarVM(
    private val getDollarListUseCase: GetDollarListUseCase,
    private val insertDollarUseCase: InsertDollarUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DollarState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<DollarEffects>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        loadList()
    }

    fun onEvent(event: DollarEvents) {
        when (event) {
            is DollarEvents.OnOfficialChange -> {
                _uiState.update { it.copy(officialInput = event.value) }
            }
            is DollarEvents.OnParallelChange -> {
                _uiState.update { it.copy(parallelInput = event.value) }
            }
            DollarEvents.OnAddRecord -> addRecord()
        }
    }

    private fun loadList() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val list = getDollarListUseCase()
            _uiState.update { it.copy(isLoading = false, list = list) }
        }
    }

    private fun addRecord() {
        val official = _uiState.value.officialInput
        val parallel = _uiState.value.parallelInput
        if (official.isBlank() || parallel.isBlank()) {
            viewModelScope.launch {
                _uiEffect.emit(DollarEffects.ShowError("Completa ambos valores"))
            }
            return
        }
        viewModelScope.launch {
            insertDollarUseCase(
                DollarModel(
                    dollarOfficial = official,
                    dollarParallel = parallel
                )
            )
            _uiState.update { it.copy(officialInput = "", parallelInput = "") }
            loadList()
        }
    }
}