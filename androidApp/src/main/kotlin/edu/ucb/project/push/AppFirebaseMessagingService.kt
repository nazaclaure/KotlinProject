package edu.ucb.project.push

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import edu.ucb.project.push.domain.usecase.RefreshPushTokenUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {

    private val refreshPushToken: RefreshPushTokenUseCase by inject()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // REFRESH TOKEN: Firebase llama aqui cuando renueva el token del dispositivo
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "TOKEN RENOVADO: $token")
        scope.launch { refreshPushToken(token) }
    }

    // Se ejecuta SIEMPRE con la app en foreground.
    // En background solo se ejecuta si el mensaje es de tipo "data";
    // si trae "notification", Android muestra la notificacion por su cuenta.
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val titulo = message.notification?.title
            ?: message.data["titulo"]
            ?: "Notificación"
        val contenido = message.notification?.body
            ?: message.data["contenido"]
            ?: ""
        // Prioridad: dato "message_id" del payload; si no viene, el id propio del mensaje FCM
        val messageId = message.data[NotificationHelper.EXTRA_MESSAGE_ID]
            ?: message.messageId

        Log.d(TAG, "Mensaje recibido [$messageId]: $titulo - $contenido")
        NotificationHelper.mostrar(this, titulo, contenido, messageId)
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "FCM_SERVICE"
    }
}