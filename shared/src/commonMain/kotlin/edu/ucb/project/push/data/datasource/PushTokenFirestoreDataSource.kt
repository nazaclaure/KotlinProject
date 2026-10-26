package edu.ucb.project.push.data.datasource

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

// E50-3: guarda el token FCM en Firestore: usuarios/{uid} -> campo "token"
class PushTokenFirestoreDataSource {
    private val firestore by lazy { Firebase.firestore }

    suspend fun save(userId: String, email: String, token: String) {
        firestore.collection("usuarios").document(userId).set(
            mapOf(
                "token" to token,
                "email" to email,
                "platform" to "android"
            )
        )
    }

    suspend fun remove(userId: String) {
        firestore.collection("usuarios").document(userId).delete()
    }
}