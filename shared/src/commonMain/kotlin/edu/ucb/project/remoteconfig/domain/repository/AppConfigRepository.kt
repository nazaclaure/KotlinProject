package edu.ucb.project.remoteconfig.domain.repository

import edu.ucb.project.remoteconfig.domain.model.AppConfig

data class AppConfigResult(
    val config: AppConfig,
    val isOffline: Boolean
)

interface AppConfigRepository {

    suspend fun getConfig(): Result<AppConfig>

    suspend fun getConfigWithCache(): AppConfigResult
}