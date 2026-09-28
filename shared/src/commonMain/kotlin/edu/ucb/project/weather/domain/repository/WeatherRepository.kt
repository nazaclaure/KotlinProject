package edu.ucb.project.weather.domain.repository

import edu.ucb.project.weather.domain.model.WeatherModel

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): Result<WeatherModel>
}