package edu.ucb.project.di

import edu.ucb.project.config.AppDatabase
import edu.ucb.project.config.getDatabaseBuilder
import edu.ucb.project.remoteconfig.data.cache.AndroidConfigCache
import edu.ucb.project.remoteconfig.data.cache.ConfigCache
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {

    single<AppDatabase> {
        getDatabaseBuilder(get()).build()
    }

    single<ConfigCache> {
        AndroidConfigCache(get())
    }
}