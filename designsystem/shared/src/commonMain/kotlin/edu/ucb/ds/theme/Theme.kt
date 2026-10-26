package edu.ucb.ds.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// TOKENS DE ESPACIADO
data class DsSpacing(
    val extraSmall: Dp = 2.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 24.dp,
    val huge: Dp = 32.dp
)

// TOKENS DE BORDES REDONDEADOS
data class DsRadius(
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp,
    val full: Dp = 50.dp
)

val LocalDsColors = staticCompositionLocalOf { LightDsColors }
val LocalDsTypography = staticCompositionLocalOf { DefaultDsTypography }
val LocalDsSpacing = staticCompositionLocalOf { DsSpacing() }
val LocalDsRadius = staticCompositionLocalOf { DsRadius() }

object DsTheme {
    val colors: DsColors
        @Composable
        @ReadOnlyComposable
        get() = LocalDsColors.current

    val typography: DsTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalDsTypography.current

    val spacing: DsSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalDsSpacing.current

    val radius: DsRadius
        @Composable
        @ReadOnlyComposable
        get() = LocalDsRadius.current
}

@Composable
fun DsTheme(
    mode: DsThemeMode = DsThemeMode.LIGHT,
    colors: DsColors = colorsFor(mode),
    typography: DsTypography = DefaultDsTypography,
    spacing: DsSpacing = DsSpacing(),
    radius: DsRadius = DsRadius(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalDsColors provides colors,
        LocalDsTypography provides typography,
        LocalDsSpacing provides spacing,
        LocalDsRadius provides radius,
        content = content
    )
}