package edu.ucb.project.logs.domain.usecase

import edu.ucb.project.logs.domain.model.LogModel
import edu.ucb.project.logs.domain.repository.LogsRepository

class SendLogsUseCase(
    private val repository: LogsRepository
) {
    suspend operator fun invoke(logs: List<LogModel>): Result<Unit> {
        return repository.send(logs)
    }
}