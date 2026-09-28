package edu.ucb.project.weather.presentation.state

sealed interface WeatherEvents {
    data class OnLatitudeChange(val value: String) : WeatherEvents
    data class OnLongitudeChange(val value: String) : WeatherEvents
    object Search : WeatherEvents
}