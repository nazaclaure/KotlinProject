package edu.ucb.project.remoteconfig.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.project.remoteconfig.domain.usecase.GetAppConfigUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ConfigVM(
    private val getAppConfigUseCase: GetAppConfigUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfigState())
    val uiState: StateFlow<ConfigState> = _uiState.asStateFlow()

    private var autoRefreshJob: Job? = null

    fun onEvent(event: ConfigEvents) {
        when (event) {
            ConfigEvents.LoadConfig -> {
                loadConfig(showLoading = true)
                startAutoRefresh()
            }

            ConfigEvents.RefreshConfig -> {
                loadConfig(showLoading = true)
            }
        }
    }

    private fun startAutoRefresh() {

        if (autoRefreshJob?.isActive == true) {
            return
        }

        autoRefreshJob = viewModelScope.launch {

            while (isActive) {

                delay(AUTO_REFRESH_MS)

                loadConfig(
                    showLoading = false
                )
            }
        }
    }

    private fun loadConfig(
        showLoading: Boolean
    ) {
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = showLoading,
                    error = null
                )
            }

            try {

                val result =
                    getAppConfigUseCase.getWithCache()

                val config =
                    result.config

                _uiState.update {
                    it.copy(
                        welcomeMessage =
                            config.welcomeMessage,

                        maxItems =
                            config.maxItems,

                        featureEnabled =
                            config.featureEnabled,

                        isOffline =
                            result.isOffline,

                        isLoading =
                            false
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    companion object {
        private const val AUTO_REFRESH_MS =
            15_000L
    }
}