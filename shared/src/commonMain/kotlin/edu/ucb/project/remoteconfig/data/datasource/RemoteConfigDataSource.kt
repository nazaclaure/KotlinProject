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
            KEY_PROMO to AppConfig.DEFAULT.mostrarPromo,

            KEY_WELCOME_MESSAGE to AppConfig.DEFAULT.welcomeMessage,
            KEY_MAX_ITEMS to AppConfig.DEFAULT.maxItems,
            KEY_FEATURE_ENABLED to AppConfig.DEFAULT.featureEnabled
        )

        remoteConfig.fetch(0.seconds)
        remoteConfig.activate()

        val config = AppConfig(
            mensajeBienvenida =
                remoteConfig.getValue(KEY_MENSAJE).asString(),

            mostrarPromo =
                remoteConfig.getValue(KEY_PROMO).asBoolean(),

            welcomeMessage =
                remoteConfig.getValue(KEY_WELCOME_MESSAGE).asString(),

            maxItems =
                remoteConfig.getValue(KEY_MAX_ITEMS).asLong(),

            featureEnabled =
                remoteConfig.getValue(KEY_FEATURE_ENABLED).asBoolean()
        )

        println("REMOTE_CONFIG: $config")

        return config
    }

    companion object {
        const val KEY_MENSAJE = "mensaje_bienvenida"
        const val KEY_PROMO = "mostrar_promo"

        const val KEY_WELCOME_MESSAGE = "welcome_message"
        const val KEY_MAX_ITEMS = "max_items"
        const val KEY_FEATURE_ENABLED = "feature_enabled"
    }
}