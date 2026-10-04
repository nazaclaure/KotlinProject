package edu.ucb.project.dollar.domain.usecase

import edu.ucb.project.dollar.domain.model.DollarModel
import edu.ucb.project.dollar.domain.repository.DollarRepository

class GetDollarListUseCase(
    private val repository: DollarRepository
) {
    suspend operator fun invoke(): List<DollarModel> {
        return repository.getList()
    }
}