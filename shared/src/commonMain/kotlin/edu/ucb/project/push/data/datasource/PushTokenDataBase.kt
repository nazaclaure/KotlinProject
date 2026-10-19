package edu.ucb.project.push.data.datasource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.database

// Guarda los tokens en Realtime Database: device_tokens/{token} -> datos del usuario
class PushTokenDataBase {
    private val database by lazy { Firebase.database }

    suspend fun save(token: String, userId: String, email: String) {
        database.reference("device_tokens/$token").setValue(
            mapOf(
                "userId" to userId,
                "email" to email,
                "platform" to "android"
            )
        )
    }

    suspend fun remove(token: String) {
        database.reference("device_tokens/$token").removeValue()
    }
}