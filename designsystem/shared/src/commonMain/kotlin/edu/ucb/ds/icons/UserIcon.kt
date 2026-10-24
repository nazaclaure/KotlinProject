package edu.ucb.ds.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val UserIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "UserIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            // cabeza
            moveTo(12f, 4f)
            curveTo(9.79f, 4f, 8f, 5.79f, 8f, 8f)
            curveTo(8f, 10.21f, 9.79f, 12f, 12f, 12f)
            curveTo(14.21f, 12f, 16f, 10.21f, 16f, 8f)
            curveTo(16f, 5.79f, 14.21f, 4f, 12f, 4f)
            close()
            // cuerpo
            moveTo(12f, 14f)
            curveTo(9.33f, 14f, 4f, 15.34f, 4f, 18f)
            lineTo(4f, 20f)
            lineTo(20f, 20f)
            lineTo(20f, 18f)
            curveTo(20f, 15.34f, 14.67f, 14f, 12f, 14f)
            close()
        }
    }.build()
}