package edu.ucb.project.scheduler

// Programa tareas en segundo plano. Cada plataforma define su implementacion.
expect class SyncScheduler(context: Any?) {
    // Sincroniza Room con Firestore cada 15 minutos (minimo de WorkManager)
    fun schedulePeriodicalSync()

    // Sincroniza una sola vez, lo antes posible
    fun syncNow()
}