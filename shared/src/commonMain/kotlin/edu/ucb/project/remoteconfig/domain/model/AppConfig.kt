package edu.ucb.project.remoteconfig.domain.model

data class AppConfig(
    val mensajeBienvenida: String,
    val mostrarPromo: Boolean
) {
    companion object {
        // Valores por defecto si Firebase no responde
        val DEFAULT = AppConfig(
            mensajeBienvenida = "Bienvenido",
            mostrarPromo = false
        )
    }
}