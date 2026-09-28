package edu.ucb.project.weather.data.mapper

import edu.ucb.project.weather.data.dto.WeatherDto
import edu.ucb.project.weather.domain.model.WeatherModel

fun WeatherDto.toModel(): WeatherModel = WeatherModel(
    temperature = currentWeather.temperature,
    windspeed = currentWeather.windspeed,
    winddirection = currentWeather.winddirection,
    weathercode = currentWeather.weathercode,
    time = currentWeather.time,
    latitude = latitude,
    longitude = longitude
)