package edu.ucb.project.remoteconfig.data.datasource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.remoteConfig
import edu.ucb.project.remoteconfig.domain.model.AppConfig
import kotlin.time.Duration.Companion.seconds

class RemoteConfigDataSource {

    private val remoteConfig = Firebase.remoteConfig

    suspend fun fetch(): AppConfig {
        remoteConfig.setDefaults(
            KEY_MENSAJE to AppConfig.DEFAULT.mensajeBienvenida,
            KEY_PROMO to AppConfig.DEFAULT.mostrarPromo
        )
        // 0 = consulta a Firebase siempre, sin esperar el cache de 12 horas (util en desarrollo)
        remoteConfig.fetch(0.seconds)
        remoteConfig.activate()

        val config = AppConfig(
            mensajeBienvenida = remoteConfig.getValue(KEY_MENSAJE).asString(),
            mostrarPromo = remoteConfig.getValue(KEY_PROMO).asBoolean()
        )
        println("REMOTE_CONFIG: $config")
        return config
    }

    companion object {
        const val KEY_MENSAJE = "mensaje_bienvenida"
        const val KEY_PROMO = "mostrar_promo"
    }
}