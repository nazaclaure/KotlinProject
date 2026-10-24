package edu.ucb.ds.theme

import androidx.compose.ui.graphics.Color

// TOKENS DE COLOR: cada modo (claro, oscuro, alto contraste) define los mismos nombres
data class DsColors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val error: Color,
    val onError: Color,
    val outline: Color
)

// Modo claro
val LightDsColors = DsColors(
    primary = Color(0xFF1E88E5),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF43A047),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFFFAFAFA),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1C1B1F),
    error = Color(0xFFD32F2F),
    onError = Color(0xFFFFFFFF),
    outline = Color(0xFF79747E)
)

// Modo oscuro
val DarkDsColors = DsColors(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF00325B),
    secondary = Color(0xFFA5D6A7),
    onSecondary = Color(0xFF0B3D12),
    background = Color(0xFF121212),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE6E1E5),
    error = Color(0xFFEF9A9A),
    onError = Color(0xFF601410),
    outline = Color(0xFF938F99)
)

// Modo alto contraste (fondo negro, texto blanco, colores muy saturados)
val HighContrastDsColors = DsColors(
    primary = Color(0xFFFFFF00),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF00FFFF),
    onSecondary = Color(0xFF000000),
    background = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF000000),
    onSurface = Color(0xFFFFFFFF),
    error = Color(0xFFFF8A80),
    onError = Color(0xFF000000),
    outline = Color(0xFFFFFFFF)
)

// Modos disponibles en el Design System
enum class DsThemeMode { LIGHT, DARK, HIGH_CONTRAST }

fun colorsFor(mode: DsThemeMode): DsColors = when (mode) {
    DsThemeMode.LIGHT -> LightDsColors
    DsThemeMode.DARK -> DarkDsColors
    DsThemeMode.HIGH_CONTRAST -> HighContrastDsColors
}