package edu.ucb.project.dollar.domain.usecase

import edu.ucb.project.dollar.domain.repository.DollarRepository

class SyncDollarUseCase(
    private val repository: DollarRepository
) {
    suspend operator fun invoke() {
        repository.sync()
    }
}