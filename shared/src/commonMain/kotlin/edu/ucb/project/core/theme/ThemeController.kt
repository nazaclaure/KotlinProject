package edu.ucb.project.core.theme

import androidx.compose.runtime.staticCompositionLocalOf

// Lo que el usuario elige: seguir al sistema o forzar un modo
enum class ThemeChoice { SYSTEM, LIGHT, DARK, HIGH_CONTRAST }

// Permite leer y cambiar la eleccion de tema desde cualquier pantalla
class ThemeController(
    val choice: ThemeChoice,
    val onChoiceChange: (ThemeChoice) -> Unit
)

val LocalThemeController = staticCompositionLocalOf {
    ThemeController(ThemeChoice.SYSTEM) {}
}