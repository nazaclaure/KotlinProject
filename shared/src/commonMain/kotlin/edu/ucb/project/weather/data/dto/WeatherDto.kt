package edu.ucb.project.weather.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val latitude: Double,
    val longitude: Double,
    @SerialName("current_weather")
    val currentWeather: CurrentWeatherDto
)
//refleja lo del json, dos clases anidadas, el de abajo traduce el snake_case, sino falla
@Serializable
data class CurrentWeatherDto(
    val temperature: Double,
    val windspeed: Double,
    val winddirection: Double,
    val weathercode: Int,
    val time: String
)