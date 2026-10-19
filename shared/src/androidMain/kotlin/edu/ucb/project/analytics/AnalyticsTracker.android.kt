package edu.ucb.project.analytics

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent

actual class AnalyticsTracker actual constructor() {

    private val analytics = Firebase.analytics

    actual fun logEvent(name: String, params: Map<String, String>) {
        analytics.logEvent(name) {
            params.forEach { (key, value) -> param(key, value) }
        }
    }

    actual fun setUser(userId: String?) {
        analytics.setUserId(userId)
    }
}