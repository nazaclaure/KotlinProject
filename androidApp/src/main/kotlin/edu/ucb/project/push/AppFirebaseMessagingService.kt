package edu.ucb.project.push

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppFirebaseMessagingService : FirebaseMessagingService() {

    // REFRESH TOKEN: Firebase llama aqui cuando renueva el token del dispositivo
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "TOKEN RENOVADO: $token")
        // Siguiente paso: enviar el token nuevo al backend / base de datos
    }

    // Llega un mensaje con la app abierta
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val titulo = message.notification?.title
            ?: message.data["titulo"]
            ?: "Notificación"
        val contenido = message.notification?.body
            ?: message.data["contenido"]
            ?: ""

        Log.d(TAG, "Mensaje recibido: $titulo - $contenido")
        NotificationHelper.mostrar(this, titulo, contenido)
    }

    companion object {
        private const val TAG = "FCM_SERVICE"
    }
}