package edu.ucb.project.remoteconfig.domain.usecase

import edu.ucb.project.remoteconfig.domain.model.AppConfig
import edu.ucb.project.remoteconfig.domain.repository.AppConfigRepository
import edu.ucb.project.remoteconfig.domain.repository.AppConfigResult

class GetAppConfigUseCase(
    private val repository: AppConfigRepository
) {

    suspend operator fun invoke(): AppConfig {
        return repository
            .getConfig()
            .getOrDefault(AppConfig.DEFAULT)
    }

    suspend fun getWithCache(): AppConfigResult {
        return repository.getConfigWithCache()
    }
}