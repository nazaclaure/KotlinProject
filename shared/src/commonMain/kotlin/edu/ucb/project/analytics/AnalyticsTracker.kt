package edu.ucb.project.analytics

// Registra eventos de analitica. Cada plataforma define su implementacion.
expect class AnalyticsTracker() {
    fun logEvent(name: String, params: Map<String, String>)
    fun setUser(userId: String?)
}