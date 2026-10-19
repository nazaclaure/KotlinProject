package edu.ucb.project.push.data.provider

import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class PushTokenProvider actual constructor() {

    actual suspend fun getToken(): String? = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { tarea ->
            continuation.resume(if (tarea.isSuccessful) tarea.result else null)
        }
    }

    actual suspend fun deleteToken() = suspendCoroutine<Unit> { continuation ->
        FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener {
            continuation.resume(Unit)
        }
    }
}