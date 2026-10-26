package edu.ucb.project.remoteconfig.data.cache

import edu.ucb.project.remoteconfig.domain.model.AppConfig

interface ConfigCache {

    fun save(config: AppConfig)

    fun load(): AppConfig?
}