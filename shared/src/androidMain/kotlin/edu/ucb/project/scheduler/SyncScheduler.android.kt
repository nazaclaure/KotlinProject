package edu.ucb.project.scheduler

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

actual class SyncScheduler actual constructor(
    context: Any?
) {
    private val androidContext = context as? Context
        ?: throw IllegalArgumentException("Context must be an instance of android.content.Context")

    private val periodicWorkName = "syncDollarPeriodic"
    private val oneTimeWorkName = "syncDollarNow"
    private val intervalMinutes = 15L

    // Solo corre si hay internet
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    actual fun schedulePeriodicalSync() {
        val request = PeriodicWorkRequest.Builder(
            SyncWorker::class.java,
            intervalMinutes,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        // KEEP: si ya esta programada, no se duplica
        WorkManager.getInstance(androidContext.applicationContext).enqueueUniquePeriodicWork(
            periodicWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    actual fun syncNow() {
        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(androidContext.applicationContext).enqueueUniqueWork(
            oneTimeWorkName,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}