package edu.ucb.project.remoteconfig.domain.repository

import edu.ucb.project.remoteconfig.domain.model.AppConfig

interface AppConfigRepository {
    suspend fun getConfig(): Result<AppConfig>
}