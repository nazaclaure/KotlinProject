package edu.ucb.project

import android.app.Application
import edu.ucb.project.di.initKoinAndroid
import edu.ucb.project.scheduler.SyncScheduler

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinAndroid(this)

        // Sincronizacion en segundo plano (WorkManager)
        val scheduler = SyncScheduler(this)
        scheduler.schedulePeriodicalSync()
        scheduler.syncNow()
    }
}