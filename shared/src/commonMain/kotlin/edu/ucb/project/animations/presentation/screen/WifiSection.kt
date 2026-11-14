package edu.ucb.project.animations.presentation.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinproject.shared.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun WifiSection() {
    // Reloj infinito: va de 0 a 1 y vuelve a empezar (igual que el skeleton)
    val reloj = rememberInfiniteTransition(label = "wifi")
    val avance by reloj.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "avance"
    )

    val colorLogo = MaterialTheme.colorScheme.primary
    val colorBase = colorLogo.copy(alpha = 0.3f)
    val descripcion = stringResource(Res.string.anim_10_cd)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.anim_10_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(Res.string.anim_10_text),
                style = MaterialTheme.typography.bodyMedium
            )

            Box(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .size(140.dp)
                        .semantics { contentDescription = descripcion }
                ) {
                    // La franja de luz se mueve de izquierda a derecha
                    val ancho = size.width
                    val inicio = -ancho + avance * ancho * 2
                    val brillo = Brush.linearGradient(
                        colors = listOf(colorBase, colorLogo, colorBase),
                        start = Offset(inicio, 0f),
                        end = Offset(inicio + ancho, 0f)
                    )

                    val centro = Offset(size.width / 2, size.height * 0.85f)
                    val grosor = size.width * 0.07f

                    // Punto de abajo
                    drawCircle(
                        brush = brillo,
                        radius = size.width * 0.06f,
                        center = centro
                    )

                    // Las tres barras (arcos), de chica a grande
                    val radios = listOf(0.22f, 0.38f, 0.54f)
                    for (i in 0..2) {
                        val r = size.width * radios[i]
                        drawArc(
                            brush = brillo,
                            startAngle = -135f,
                            sweepAngle = 90f,
                            useCenter = false,
                            topLeft = Offset(centro.x - r, centro.y - r),
                            size = Size(r * 2, r * 2),
                            style = Stroke(width = grosor, cap = StrokeCap.Round)
                        )
                    }
                }
            }
        }
    }
}