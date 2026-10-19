package edu.ucb.project.push.data.repository

import edu.ucb.project.push.data.datasource.PushTokenDataBase
import edu.ucb.project.push.data.provider.PushTokenProvider
import edu.ucb.project.push.domain.repository.PushTokenRepository

class PushTokenRepositoryImpl(
    private val provider: PushTokenProvider,
    private val dataBase: PushTokenDataBase
) : PushTokenRepository {

    // Al iniciar sesion: obtiene el token y lo guarda asociado al usuario
    override suspend fun register(userId: String, email: String): Result<Unit> = runCatching {
        val token = provider.getToken() ?: error("No se pudo obtener el token FCM")
        dataBase.save(token, userId, email)
    }

    // Al cerrar sesion: borra el token de la base de datos y de Firebase
    override suspend fun unregister(): Result<Unit> = runCatching {
        val token = provider.getToken()
        if (token != null) dataBase.remove(token)
        provider.deleteToken()
    }
}