package edu.ucb.project.weather.domain.model

data class WeatherModel(
    val temperature: Double,
    val windspeed: Double,
    val winddirection: Double,
    val weathercode: Int,
    val time: String,
    val latitude: Double,
    val longitude: Double
)
//domain regla de negocios, que necesita la app
//7 campos, modelo de datos limpios/planos, no como dto directo de jsn