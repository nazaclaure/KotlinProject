package edu.ucb.project.dollar.domain.usecase

import edu.ucb.project.dollar.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow

class ObserveExchangeUseCase(
    val repository: ExchangeRepository
) {
    suspend fun invoke(): Flow<String?> {
        return repository.observe()
    }
}