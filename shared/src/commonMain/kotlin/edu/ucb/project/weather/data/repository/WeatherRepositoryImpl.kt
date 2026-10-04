package edu.ucb.project.weather.data.repository

import edu.ucb.project.weather.data.datasource.WeatherRemoteDataSource
import edu.ucb.project.weather.data.mapper.toModel
import edu.ucb.project.weather.domain.model.WeatherModel
import edu.ucb.project.weather.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val dataSource: WeatherRemoteDataSource
) : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double): Result<WeatherModel> {
        return try {
            Result.success(dataSource.fetchWeather(latitude, longitude).toModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

//implementa el repo, llama a datasource y envuelve el resultado en reuslt, vm no manejara excepciones por el try catch