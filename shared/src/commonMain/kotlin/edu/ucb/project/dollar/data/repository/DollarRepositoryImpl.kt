package edu.ucb.project.dollar.data.repository

import edu.ucb.project.dollar.data.datasource.DollarLocalDataSource
import edu.ucb.project.dollar.domain.model.DollarModel
import edu.ucb.project.dollar.domain.repository.DollarRepository

class DollarRepositoryImpl(
    private val localDataSource: DollarLocalDataSource
) : DollarRepository {

    override suspend fun getList(): List<DollarModel> {
        return localDataSource.getList()
    }

    override suspend fun insert(dollar: DollarModel) {
        localDataSource.insert(dollar)
    }
}