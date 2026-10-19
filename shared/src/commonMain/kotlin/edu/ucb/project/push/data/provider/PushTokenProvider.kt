package edu.ucb.project.push.data.provider

// Obtiene y elimina el token FCM del dispositivo (solo implementado en Android)
expect class PushTokenProvider() {
    suspend fun getToken(): String?
    suspend fun deleteToken()
}