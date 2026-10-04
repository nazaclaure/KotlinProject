package edu.ucb.project.dollar.domain.usecase

import edu.ucb.project.dollar.domain.model.DollarModel
import edu.ucb.project.dollar.domain.repository.DollarRepository

class InsertDollarUseCase(
    private val repository: DollarRepository
) {
    suspend operator fun invoke(dollar: DollarModel) {
        repository.insert(dollar)
    }
}