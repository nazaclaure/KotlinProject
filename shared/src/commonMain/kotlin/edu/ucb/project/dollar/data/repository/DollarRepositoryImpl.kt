package edu.ucb.project.dollar.data.repository

import edu.ucb.project.dollar.data.dao.DollarDao
import edu.ucb.project.dollar.data.entity.DollarEntity
import edu.ucb.project.dollar.domain.model.DollarModel
import edu.ucb.project.dollar.domain.repository.DollarRepository

class DollarRepositoryImpl(
    private val dao: DollarDao
) : DollarRepository {

    override suspend fun getList(): List<DollarModel> {
        return dao.getList().map {
            DollarModel(
                dollarOfficial = it.dollarOfficial,
                dollarParallel = it.dollarParallel,
                timestamp = it.timestamp
            )
        }
    }

    override suspend fun insert(dollar: DollarModel) {
        dao.insert(
            DollarEntity(
                dollarOfficial = dollar.dollarOfficial,
                dollarParallel = dollar.dollarParallel,
                timestamp = dollar.timestamp
            )
        )
    }
}