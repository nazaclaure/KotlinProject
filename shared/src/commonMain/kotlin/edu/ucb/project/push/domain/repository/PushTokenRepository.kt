package edu.ucb.project.push.domain.repository

interface PushTokenRepository {
    suspend fun register(userId: String, email: String): Result<Unit>
    suspend fun unregister(): Result<Unit>
    suspend fun refresh(newToken: String): Result<Unit>
}