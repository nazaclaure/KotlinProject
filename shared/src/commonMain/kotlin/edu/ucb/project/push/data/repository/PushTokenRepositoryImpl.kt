package edu.ucb.project.push.data.repository

import edu.ucb.project.push.data.datasource.PushTokenDataBase
import edu.ucb.project.push.data.provider.PushTokenProvider
import edu.ucb.project.push.domain.repository.PushTokenRepository

class PushTokenRepositoryImpl(
    private val provider: PushTokenProvider,
    private val dataBase: PushTokenDataBase
) : PushTokenRepository {

    // Usuario con sesion iniciada y su token actual (se pierde si se cierra el proceso)
    private data class Session(val userId: String, val email: String, val token: String)
    private var session: Session? = null

    // Al iniciar sesion: obtiene el token y lo guarda asociado al usuario
    override suspend fun register(userId: String, email: String): Result<Unit> = runCatching {
        val token = provider.getToken() ?: error("No se pudo obtener el token FCM")
        dataBase.save(token, userId, email)
        session = Session(userId, email, token)
    }

    // Al cerrar sesion: borra el token de la base de datos y de Firebase
    override suspend fun unregister(): Result<Unit> = runCatching {
        val token = session?.token ?: provider.getToken()
        if (token != null) dataBase.remove(token)
        provider.deleteToken()
        session = null
    }

    // Cuando Firebase renueva el token: reemplaza el viejo por el nuevo
    override suspend fun refresh(newToken: String): Result<Unit> = runCatching {
        val actual = session ?: return@runCatching
        dataBase.remove(actual.token)
        dataBase.save(newToken, actual.userId, actual.email)
        session = actual.copy(token = newToken)
    }
}