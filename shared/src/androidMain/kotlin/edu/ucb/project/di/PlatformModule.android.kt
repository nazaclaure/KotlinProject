package edu.ucb.project.di

import org.koin.core.module.Module
import org.koin.dsl.module
import edu.ucb.project.config.AppDatabase
import edu.ucb.project.config.getDatabaseBuilder

actual fun platformModule(): Module = module {
    single<AppDatabase> {
        getDatabaseBuilder(get()).build()
    }
}