package edu.ucb.project.weather.domain.usecase

import edu.ucb.project.weather.domain.model.WeatherModel
import edu.ucb.project.weather.domain.repository.WeatherRepository

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Result<WeatherModel> =
        repository.getWeather(latitude, longitude)
}