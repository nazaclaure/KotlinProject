package edu.ucb.project.scheduler

import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import edu.ucb.project.dollar.domain.usecase.SyncDollarUseCase
import edu.ucb.project.logs.domain.model.LogLevel
import edu.ucb.project.logs.domain.model.LogModel
import edu.ucb.project.logs.domain.usecase.SendLogsUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// Trabajo en segundo plano: une los datos de Room y Firestore y reporta el resultado por gRPC
class SyncWorker(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters), KoinComponent {

    private val syncDollar: SyncDollarUseCase by inject()
    private val sendLogs: SendLogsUseCase by inject()

    override suspend fun doWork(): Result {
        Log.d(TAG, "Sincronizando Room con Firestore...")
        return try {
            syncDollar()
            Log.d(TAG, "Sincronizacion terminada")
            reportLog(LogLevel.INFO, "Sincronizacion terminada", "")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Fallo la sincronizacion: ${e.message}")
            reportLog(LogLevel.ERROR, "Fallo la sincronizacion: ${e.message}", e.stackTraceToString())
            Result.retry()
        }
    }

    // Manda el log al servidor por gRPC. Si el servidor no responde, no afecta la sincronizacion.
    private suspend fun reportLog(level: LogLevel, message: String, stackTrace: String) {
        try {
            val androidId = Settings.Secure.getString(
                applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
            ) ?: "unknown"
            val log = LogModel(
                androidId = androidId,
                appInstanceId = applicationContext.packageName,
                level = level,
                message = message,
                stackTrace = stackTrace,
                timestamp = System.currentTimeMillis(),
                versionCode = 1,
                userId = "sync-worker"
            )
            val result = sendLogs(listOf(log))
            Log.d(TAG, "Log gRPC enviado: " + result)
        } catch (e: Exception) {
            Log.w(TAG, "No se pudo enviar el log: ${e.message}")
        }
    }

    companion object {
        private const val TAG = "SyncWorker"
    }
}