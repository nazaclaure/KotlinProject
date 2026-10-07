package edu.ucb.project.dollar.data.repository

import edu.ucb.project.dollar.data.datasource.RealTimeDataBase
import edu.ucb.project.dollar.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow

class ExchangeRepositoryImpl(
    val realTimeDataBase: RealTimeDataBase
) : ExchangeRepository {
    override suspend fun observe(): Flow<String?> {
        return realTimeDataBase.observeMessage()
    }
}