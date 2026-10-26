package edu.ucb.project.push.domain.usecase

import edu.ucb.project.push.domain.repository.PushTokenRepository

class UnregisterPushTokenUseCase(private val repository: PushTokenRepository) {
    suspend operator fun invoke(): Result<Unit> = repository.unregister()
}