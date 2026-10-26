package edu.ucb.project.remoteconfig.domain.model

data class AppConfig(
    val mensajeBienvenida: String,
    val mostrarPromo: Boolean,

    val welcomeMessage: String,
    val maxItems: Long,
    val featureEnabled: Boolean
) {
    companion object {
        val DEFAULT = AppConfig(
            mensajeBienvenida = "Bienvenido",
            mostrarPromo = false,

            welcomeMessage = "Bienvenido",
            maxItems = 10,
            featureEnabled = false
        )
    }
}