package edu.ucb.project.dollar.data.datasource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import edu.ucb.project.dollar.domain.model.DollarModel
import kotlinx.serialization.Serializable

// Documento que se guarda en Firestore (coleccion "dollars")
@Serializable
data class DollarDocument(
    val dollarOfficial: String? = null,
    val dollarParallel: String? = null,
    val timestamp: Long = 0
)

class DollarFirestoreDataSource {

    private val collection = Firebase.firestore.collection("dollars")

    // El id del documento es el timestamp: guardar dos veces el mismo registro no lo duplica
    suspend fun save(dollar: DollarModel) {
        collection.document(dollar.timestamp.toString()).set(
            DollarDocument(
                dollarOfficial = dollar.dollarOfficial,
                dollarParallel = dollar.dollarParallel,
                timestamp = dollar.timestamp
            )
        )
    }

    suspend fun getAll(): List<DollarModel> {
        return collection.get().documents.map { snapshot ->
            val doc = snapshot.data<DollarDocument>()
            DollarModel(
                dollarOfficial = doc.dollarOfficial,
                dollarParallel = doc.dollarParallel,
                timestamp = doc.timestamp
            )
        }
    }
}