package edu.ucb.project.dollar.domain.repository

import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    suspend fun observe(): Flow<String?>
}