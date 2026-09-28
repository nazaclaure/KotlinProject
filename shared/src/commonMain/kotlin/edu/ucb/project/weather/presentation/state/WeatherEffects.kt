package edu.ucb.project.weather.presentation.state

sealed interface WeatherEffects {
    data class ShowError(val message: String) : WeatherEffects
}