package edu.ucb.project.core.notifications

// Puente para mostrar una notificacion local desde codigo comun.
// Cada plataforma registra su implementacion en "handler".
object LocalNotifier {
    var handler: ((String, String) -> Unit)? = null

    fun show(title: String, text: String) {
        handler?.invoke(title, text)
    }
}