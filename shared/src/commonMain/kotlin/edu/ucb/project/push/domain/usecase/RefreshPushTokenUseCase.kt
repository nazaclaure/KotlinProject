package edu.ucb.project.push.domain.usecase

import edu.ucb.project.push.domain.repository.PushTokenRepository

class RefreshPushTokenUseCase(private val repository: PushTokenRepository) {
    suspend operator fun invoke(newToken: String): Result<Unit> = repository.refresh(newToken)
}