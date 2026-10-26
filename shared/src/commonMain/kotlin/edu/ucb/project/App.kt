package edu.ucb.project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import edu.ucb.ds.theme.DsTheme
import edu.ucb.ds.theme.DsThemeMode
import edu.ucb.project.core.navigation.AppNavHost
import edu.ucb.project.core.theme.FontSizeChoice
import edu.ucb.project.core.theme.LocalThemeController
import edu.ucb.project.core.theme.ThemeChoice
import edu.ucb.project.core.theme.ThemeController

@Composable
fun App() {
    // SYSTEM sigue el tema del dispositivo; las otras opciones fuerzan un modo
    val systemDark = isSystemInDarkTheme()
    var choice by remember { mutableStateOf(ThemeChoice.SYSTEM) }
    var fontSize by remember { mutableStateOf(FontSizeChoice.NORMAL) }

    val mode = when (choice) {
        ThemeChoice.SYSTEM -> if (systemDark) DsThemeMode.DARK else DsThemeMode.LIGHT
        ThemeChoice.LIGHT -> DsThemeMode.LIGHT
        ThemeChoice.DARK -> DsThemeMode.DARK
        ThemeChoice.HIGH_CONTRAST -> DsThemeMode.HIGH_CONTRAST
    }
    val controller = remember(choice, fontSize) {
        ThemeController(
            choice = choice,
            onChoiceChange = { choice = it },
            fontSize = fontSize,
            onFontSizeChange = { fontSize = it }
        )
    }

    // El tamano elegido se multiplica por el tamano de fuente que ya tiene el sistema
    val density = LocalDensity.current
    val scaledDensity = remember(density, fontSize) {
        Density(density.density, density.fontScale * fontSize.scale)
    }

    CompositionLocalProvider(LocalDensity provides scaledDensity) {
        // El Design System define los tokens (colores, espaciado, radios) segun el modo
        DsTheme(mode = mode) {
            val c = DsTheme.colors
            val surfaceVariant = c.onSurface.copy(alpha = 0.10f).compositeOver(c.surface)
            val primaryContainer = c.primary.copy(alpha = 0.25f).compositeOver(c.surface)

            // Los mismos tokens se pasan a MaterialTheme para que todas las pantallas cambien
            val scheme = if (mode == DsThemeMode.LIGHT) {
                lightColorScheme(
                    primary = c.primary, onPrimary = c.onPrimary,
                    secondary = c.secondary, onSecondary = c.onSecondary,
                    background = c.background, onBackground = c.onBackground,
                    surface = c.surface, onSurface = c.onSurface,
                    error = c.error, onError = c.onError,
                    outline = c.outline,
                    surfaceVariant = surfaceVariant, onSurfaceVariant = c.onSurface,
                    primaryContainer = primaryContainer, onPrimaryContainer = c.onSurface
                )
            } else {
                darkColorScheme(
                    primary = c.primary, onPrimary = c.onPrimary,
                    secondary = c.secondary, onSecondary = c.onSecondary,
                    background = c.background, onBackground = c.onBackground,
                    surface = c.surface, onSurface = c.onSurface,
                    error = c.error, onError = c.onError,
                    outline = c.outline,
                    surfaceVariant = surfaceVariant, onSurfaceVariant = c.onSurface,
                    primaryContainer = primaryContainer, onPrimaryContainer = c.onSurface
                )
            }

            MaterialTheme(colorScheme = scheme) {
                CompositionLocalProvider(LocalThemeController provides controller) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavHost()
                    }
                }
            }
        }
    }
}