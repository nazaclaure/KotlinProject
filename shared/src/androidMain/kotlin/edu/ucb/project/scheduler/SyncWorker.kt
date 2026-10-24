package edu.ucb.project.scheduler

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import edu.ucb.project.dollar.domain.usecase.SyncDollarUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// Trabajo en segundo plano: une los datos de Room y Firestore
class SyncWorker(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters), KoinComponent {

    private val syncDollar: SyncDollarUseCase by inject()

    override suspend fun doWork(): Result {
        Log.d("SyncWorker", "Sincronizando Room con Firestore...")
        return try {
            syncDollar()
            Log.d("SyncWorker", "Sincronizacion terminada")
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Fallo la sincronizacion: ${e.message}")
            Result.retry()
        }
    }
}