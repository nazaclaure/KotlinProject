package edu.ucb.project.remoteconfig.data.cache

import android.content.Context
import edu.ucb.project.remoteconfig.domain.model.AppConfig

class AndroidConfigCache(
    context: Context
) : ConfigCache {

    private val preferences =
        context.getSharedPreferences(
            "remote_config_cache",
            Context.MODE_PRIVATE
        )

    override fun save(config: AppConfig) {
        preferences.edit()
            .putString("welcome_message", config.welcomeMessage)
            .putLong("max_items", config.maxItems)
            .putBoolean("feature_enabled", config.featureEnabled)
            .apply()
    }

    override fun load(): AppConfig? {

        if (!preferences.contains("welcome_message")) {
            return null
        }

        return AppConfig(
            mensajeBienvenida = AppConfig.DEFAULT.mensajeBienvenida,
            mostrarPromo = AppConfig.DEFAULT.mostrarPromo,
            welcomeMessage = preferences.getString(
                "welcome_message",
                AppConfig.DEFAULT.welcomeMessage
            ) ?: AppConfig.DEFAULT.welcomeMessage,
            maxItems = preferences.getLong(
                "max_items",
                AppConfig.DEFAULT.maxItems
            ),
            featureEnabled = preferences.getBoolean(
                "feature_enabled",
                AppConfig.DEFAULT.featureEnabled
            )
        )
    }
}