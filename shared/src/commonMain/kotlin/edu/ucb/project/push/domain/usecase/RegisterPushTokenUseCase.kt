package edu.ucb.project.push.domain.usecase

import edu.ucb.project.push.domain.repository.PushTokenRepository

class RegisterPushTokenUseCase(private val repository: PushTokenRepository) {
    suspend operator fun invoke(userId: String, email: String): Result<Unit> =
        repository.register(userId, email)
}