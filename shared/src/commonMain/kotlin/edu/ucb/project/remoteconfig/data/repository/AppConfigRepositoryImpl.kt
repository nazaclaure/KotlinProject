package edu.ucb.project.remoteconfig.data.repository

import edu.ucb.project.remoteconfig.data.datasource.RemoteConfigDataSource
import edu.ucb.project.remoteconfig.domain.model.AppConfig
import edu.ucb.project.remoteconfig.domain.repository.AppConfigRepository

class AppConfigRepositoryImpl(
    private val dataSource: RemoteConfigDataSource
) : AppConfigRepository {

    override suspend fun getConfig(): Result<AppConfig> = runCatching {
        dataSource.fetch()
    }
}