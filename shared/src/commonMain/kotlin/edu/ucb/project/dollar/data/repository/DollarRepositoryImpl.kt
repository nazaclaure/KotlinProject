package edu.ucb.project.dollar.data.repository

import edu.ucb.project.dollar.data.datasource.DollarFirestoreDataSource
import edu.ucb.project.dollar.data.datasource.DollarLocalDataSource
import edu.ucb.project.dollar.domain.model.DollarModel
import edu.ucb.project.dollar.domain.repository.DollarRepository
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Clock

// Room es la fuente principal (funciona sin internet); Firestore es la copia en la nube
class DollarRepositoryImpl(
    private val localDataSource: DollarLocalDataSource,
    private val remoteDataSource: DollarFirestoreDataSource
) : DollarRepository {

    override suspend fun getList(): List<DollarModel> {
        return localDataSource.getList()
    }

    override suspend fun insert(dollar: DollarModel) {
        // El timestamp identifica el registro en Room y en Firestore
        val record = if (dollar.timestamp == 0L) {
            dollar.copy(timestamp = Clock.System.now().toEpochMilliseconds())
        } else {
            dollar
        }
        // 1. Guardar en Room
        localDataSource.insert(record)
        // 2. Subir a Firestore (si no hay internet, no bloquea; Firestore lo envia despues)
        runCatching { withTimeoutOrNull(5_000) { remoteDataSource.save(record) } }
    }

    // Une los datos de Room y Firestore comparando por timestamp
    override suspend fun sync() {
        runCatching {
            withTimeoutOrNull(15_000) {
                val remote = remoteDataSource.getAll()
                val local = localDataSource.getList().filter { it.timestamp != 0L }
                val localKeys = local.map { it.timestamp }.toSet()
                val remoteKeys = remote.map { it.timestamp }.toSet()

                // Lo que esta en la nube y falta en Room -> se guarda en Room
                remote.filter { it.timestamp !in localKeys }
                    .forEach { localDataSource.insert(it) }

                // Lo que esta en Room y falta en la nube -> se sube a Firestore
                local.filter { it.timestamp !in remoteKeys }
                    .forEach { remoteDataSource.save(it) }
            }
        }
    }
}