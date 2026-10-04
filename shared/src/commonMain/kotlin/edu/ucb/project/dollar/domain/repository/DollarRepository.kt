package edu.ucb.project.dollar.domain.repository

import edu.ucb.project.dollar.domain.model.DollarModel

interface DollarRepository {
    suspend fun getList(): List<DollarModel>
    suspend fun insert(dollar: DollarModel)
}