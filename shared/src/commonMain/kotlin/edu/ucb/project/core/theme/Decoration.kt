package edu.ucb.project.core.theme

import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp

// Estilo comun de las pantallas: cabecera con degradado lila y rosa + hoja blanca redondeada.
// Se adapta al tema elegido: el degradado solo se usa en modo claro; en oscuro y
// alto contraste se respetan los colores del tema. Las alturas no son fijas,
// asi que tambien funciona con el tamano de texto grande.

@Composable
private fun esModoClaro(): Boolean = MaterialTheme.colorScheme.background.luminance() > 0.5f

@Composable
fun colorTituloCabecera(): Color =
    if (esModoClaro()) Color(0xFF2B2140) else MaterialTheme.colorScheme.onBackground

@Composable
fun colorSubtituloCabecera(): Color =
    if (esModoClaro()) Color(0xFF5B4E78) else MaterialTheme.colorScheme.onBackground

// Fondo con degradado y circulos de adorno
@Composable
private fun FondoLila(contenido: @Composable BoxScope.() -> Unit) {
    val claro = esModoClaro()
    val fondo = MaterialTheme.colorScheme.background

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    if (claro) listOf(Color(0xFFD9C8F5), Color(0xFFF7D3E3), Color(0xFFF7F3FC))
                    else listOf(fondo, fondo)
                )
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = 60.dp)
                .size(150.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = if (claro) 0.35f else 0f))
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = (-40).dp, y = 150.dp)
                .size(90.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = if (claro) 0.25f else 0f))
        )
        contenido()
    }
}

@Composable
fun PantallaDegradado(
    modifier: Modifier = Modifier,
    // anclada = true: la hoja queda abajo y la cabecera ocupa el resto (Login, registro)
    // anclada = false: todo se desliza junto (pantallas largas, como Perfil)
    anclada: Boolean = true,
    onVolver: (() -> Unit)? = null,
    espacio: Dp = 0.dp,
    alineacion: Alignment.Horizontal = Alignment.CenterHorizontally,
    cabecera: @Composable ColumnScope.() -> Unit,
    contenido: @Composable ColumnScope.() -> Unit
) {
    FondoLila {
        if (anclada) {
            Column(modifier = Modifier.fillMaxSize().statusBarsPadding().imePadding()) {
                if (onVolver != null) BotonVolver(onVolver)
                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 28.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = cabecera
                )
                Hoja(scroll = true, espacio = espacio, alineacion = alineacion, contenido = contenido)
            }
        } else {
            // Todo se desliza, pero la hoja siempre llega hasta el borde de abajo
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val altoPantalla = maxHeight
                val densidad = LocalDensity.current
                var altoSuperior by remember { mutableStateOf(0.dp) }

                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    Column(
                        modifier = Modifier
                            .onSizeChanged { altoSuperior = with(densidad) { it.height.toDp() } }
                            .statusBarsPadding()
                    ) {
                        if (onVolver != null) BotonVolver(onVolver)
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp, vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            content = cabecera
                        )
                    }
                    Hoja(
                        modifier = Modifier.heightIn(min = (altoPantalla - altoSuperior).coerceAtLeast(0.dp)),
                        scroll = false,
                        espacio = espacio,
                        alineacion = alineacion,
                        contenido = contenido
                    )
                }
            }
        }
    }
}

// Pantalla con titulo arriba y una hoja que ocupa el resto de la altura.
// Sirve para pantallas con listas o con su propio scroll (Catalogo, Dolar, Clima...)
@Composable
fun PantallaHoja(
    titulo: String? = null,
    onVolver: (() -> Unit)? = null,
    desplazable: Boolean = false,
    espacio: Dp = 0.dp,
    contenido: @Composable ColumnScope.() -> Unit
) {
    FondoLila {
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            if (onVolver != null) BotonVolver(onVolver)
            if (titulo != null) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorTituloCabecera(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp, vertical = 12.dp)
                )
            }
            Surface(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(if (desplazable) Modifier.verticalScroll(rememberScrollState()) else Modifier)
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(espacio),
                    content = contenido
                )
            }
        }
    }
}

@Composable
private fun BotonVolver(onVolver: () -> Unit) {
    TextButton(
        onClick = onVolver,
        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
    ) {
        Text(text = stringResource(Res.string.common_back), color = colorTituloCabecera())
    }
}

@Composable
private fun Hoja(
    scroll: Boolean,
    espacio: Dp,
    alineacion: Alignment.Horizontal,
    modifier: Modifier = Modifier,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .then(if (scroll) Modifier.verticalScroll(rememberScrollState()) else Modifier)
                .navigationBarsPadding()
                .padding(horizontal = 28.dp, vertical = 32.dp),
            horizontalAlignment = alineacion,
            verticalArrangement = Arrangement.spacedBy(espacio),
            content = contenido
        )
    }
}