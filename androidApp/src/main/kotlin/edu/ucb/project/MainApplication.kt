package edu.ucb.project

import android.app.Application
import edu.ucb.project.core.notifications.LocalNotifier
import edu.ucb.project.di.initKoinAndroid
import edu.ucb.project.push.NotificationHelper
import edu.ucb.project.scheduler.SyncScheduler

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinAndroid(this)

        // Notificaciones locales: el codigo comun las pide y Android las muestra
        LocalNotifier.handler = { titulo, texto ->
            NotificationHelper.mostrar(this, titulo, texto)
        }

        // Sincronizacion en segundo plano (WorkManager)
        val scheduler = SyncScheduler(this)
        scheduler.schedulePeriodicalSync()
        scheduler.syncNow()
    }
}