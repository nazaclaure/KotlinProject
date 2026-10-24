package edu.ucb.project.logs.data.repository

import edu.ucb.project.logs.data.datasource.LogsRemoteDataSource
import edu.ucb.project.logs.domain.model.LogModel
import edu.ucb.project.logs.domain.repository.LogsRepository

class LogsRepositoryImpl(
    private val remoteDataSource: LogsRemoteDataSource
) : LogsRepository {
    override suspend fun send(logs: List<LogModel>): Result<Unit> {
        return remoteDataSource.send(logs)
    }
}