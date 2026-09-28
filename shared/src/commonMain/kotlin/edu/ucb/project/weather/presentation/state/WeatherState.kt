package edu.ucb.project.weather.presentation.state

data class WeatherState(
    val latitudeInput: String = "",
    val longitudeInput: String = "",
    val isLoading: Boolean = false,
    val temperature: Double? = null,
    val windspeed: Double? = null,
    val winddirection: Double? = null,
    val weathercode: Int? = null,
    val time: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val error: String? = null
)