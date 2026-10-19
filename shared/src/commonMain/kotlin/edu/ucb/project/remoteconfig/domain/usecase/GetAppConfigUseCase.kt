package edu.ucb.project.remoteconfig.domain.usecase

import edu.ucb.project.remoteconfig.domain.model.AppConfig
import edu.ucb.project.remoteconfig.domain.repository.AppConfigRepository

class GetAppConfigUseCase(private val repository: AppConfigRepository) {
    // Si falla, devuelve los valores por defecto
    suspend operator fun invoke(): AppConfig =
        repository.getConfig().getOrDefault(AppConfig.DEFAULT)
}