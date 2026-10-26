package edu.ucb.project.logs

import edu.ucb.project.logs.domain.model.LogLevel
import edu.ucb.project.logs.domain.model.LogModel
import edu.ucb.project.logs.domain.usecase.SendLogsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// Envio de prueba para verificar gRPC (se puede borrar despues)
object LogsDebug : KoinComponent {
    private val sendLogs: SendLogsUseCase by inject()

    fun sendTest() {
        CoroutineScope(Dispatchers.Default).launch {
            val log = LogModel(
                androidId = "naza-android-id",
                appInstanceId = "naza-app-instance",
                level = LogLevel.INFO,
                message = "Hola desde KotlinProject con Wire",
                stackTrace = "",
                timestamp = Clock.System.now().toEpochMilliseconds(),
                versionCode = 1,
                userId = "naza"
            )
            val result = sendLogs(listOf(log))
            println("GRPC_TEST resultado: " + result)
        }
    }
}