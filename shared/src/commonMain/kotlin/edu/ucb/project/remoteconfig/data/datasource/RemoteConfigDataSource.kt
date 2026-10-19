package edu.ucb.project.remoteconfig.data.datasource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.remoteConfig
import edu.ucb.project.remoteconfig.domain.model.AppConfig

class RemoteConfigDataSource {

    private val remoteConfig = Firebase.remoteConfig

    suspend fun fetch(): AppConfig {
        remoteConfig.setDefaults(
            KEY_MENSAJE to AppConfig.DEFAULT.mensajeBienvenida,
            KEY_PROMO to AppConfig.DEFAULT.mostrarPromo
        )
        // Descarga los valores de Firebase y los activa
        remoteConfig.fetchAndActivate()

        return AppConfig(
            mensajeBienvenida = remoteConfig.getValue(KEY_MENSAJE).asString(),
            mostrarPromo = remoteConfig.getValue(KEY_PROMO).asBoolean()
        )
    }

    companion object {
        const val KEY_MENSAJE = "mensaje_bienvenida"
        const val KEY_PROMO = "mostrar_promo"
    }
}