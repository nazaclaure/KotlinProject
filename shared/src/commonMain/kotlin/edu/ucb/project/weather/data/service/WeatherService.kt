package edu.ucb.project.weather.data.service

import edu.ucb.project.weather.data.datasource.WeatherRemoteDataSource
import edu.ucb.project.weather.data.dto.WeatherDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class WeatherService : WeatherRemoteDataSource {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override suspend fun fetchWeather(latitude: Double, longitude: Double): WeatherDto {
        val url = "https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&current_weather=true"
        return client.get(url).body()
    }
}
//implementa la interfaz de datasource usa ktor, de json a kotlin, ignore para los campos extra