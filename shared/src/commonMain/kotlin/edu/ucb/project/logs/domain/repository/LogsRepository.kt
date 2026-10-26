package edu.ucb.project.logs.domain.repository

import edu.ucb.project.logs.domain.model.LogModel

interface LogsRepository {
    suspend fun send(logs: List<LogModel>): Result<Unit>
}