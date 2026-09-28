package edu.ucb.project.weather.data.datasource

import edu.ucb.project.weather.data.dto.WeatherDto

interface WeatherRemoteDataSource {
    suspend fun fetchWeather(latitude: Double, longitude: Double): WeatherDto
}