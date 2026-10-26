package edu.ucb.project.remoteconfig.data.repository

import edu.ucb.project.remoteconfig.data.cache.ConfigCache
import edu.ucb.project.remoteconfig.data.datasource.RemoteConfigDataSource
import edu.ucb.project.remoteconfig.domain.model.AppConfig
import edu.ucb.project.remoteconfig.domain.repository.AppConfigRepository
import edu.ucb.project.remoteconfig.domain.repository.AppConfigResult

class AppConfigRepositoryImpl(
    private val dataSource: RemoteConfigDataSource,
    private val cache: ConfigCache
) : AppConfigRepository {

    override suspend fun getConfig(): Result<AppConfig> = runCatching {
        dataSource.fetch()
    }

    override suspend fun getConfigWithCache(): AppConfigResult {

        return try {
            val remoteConfig = dataSource.fetch()

            cache.save(remoteConfig)

            AppConfigResult(
                config = remoteConfig,
                isOffline = false
            )
        } catch (e: Exception) {

            val cachedConfig = cache.load() ?: AppConfig.DEFAULT

            AppConfigResult(
                config = cachedConfig,
                isOffline = true
            )
        }
    }
}