package edu.ucb.project.weather.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucb.project.weather.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherVM(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<WeatherEffects>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onEvent(event: WeatherEvents) {
        when (event) {
            is WeatherEvents.OnLatitudeChange -> {
                _uiState.update { it.copy(latitudeInput = event.value) }
            }
            is WeatherEvents.OnLongitudeChange -> {
                _uiState.update { it.copy(longitudeInput = event.value) }
            }
            WeatherEvents.Search -> searchWeather()
        }
    }

    private fun searchWeather() {
        val lat = _uiState.value.latitudeInput.toDoubleOrNull()
        val lon = _uiState.value.longitudeInput.toDoubleOrNull()
        if (lat == null || lon == null) {
            viewModelScope.launch {
                _uiEffect.emit(WeatherEffects.ShowError("Latitud y longitud invalidas"))
            }
            return
        }
        if (lat < -90.0 || lat > 90.0 || lon < -180.0 || lon > 180.0) {
            viewModelScope.launch {
                _uiEffect.emit(WeatherEffects.ShowError("Latitud debe estar entre -90 y 90, longitud entre -180 y 180"))
            }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getWeatherUseCase(lat, lon).fold(
                onSuccess = { weather ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            temperature = weather.temperature,
                            windspeed = weather.windspeed,
                            winddirection = weather.winddirection,
                            weathercode = weather.weathercode,
                            time = weather.time,
                            latitude = weather.latitude,
                            longitude = weather.longitude
                        )
                    }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                    _uiEffect.emit(WeatherEffects.ShowError(e.message ?: "Error desconocido"))
                }
            )
        }
    }
}

//como su cerebro de la pantalla, valida, el launch conecta y fold en el caso de uso