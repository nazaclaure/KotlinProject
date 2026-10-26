package edu.ucb.ds.components.surfaces

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.ucb.ds.theme.DsTheme

// Superficie base: fondo y color de contenido segun el tema
@Composable
fun DsSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = DsTheme.colors.background,
        contentColor = DsTheme.colors.onBackground,
        content = content
    )
}