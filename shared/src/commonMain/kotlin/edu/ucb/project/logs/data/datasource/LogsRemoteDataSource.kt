package edu.ucb.project.logs.data.datasource

import edu.ucb.project.logs.domain.model.LogModel

interface LogsRemoteDataSource {
    suspend fun send(logs: List<LogModel>): Result<Unit>
}