package edu.ucb.project.animations.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AnimationsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }

        Text(
            text = "Animaciones",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // Animacion 3: contenido que cambia
        LikesSection()

        // Animacion 4: boton que se encoge
        BotonSection()

        // Animacion 5: lista escalonada
        ListaSection()

        // Animacion 6: tarjeta que se expande
        TarjetaSection()

        // Animacion 7: progreso animado
        ProgresoSection()
    }
}

@Composable
private fun LikesSection() {
    // Estados: cuando cambian, Compose anima solo
    var meGusta by remember { mutableStateOf(false) }
    var likes by remember { mutableStateOf(128) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "3. Contenido que cambia",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Toca el corazon y mira como cambia el numero.",
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = {
                        meGusta = !meGusta
                        likes = if (meGusta) likes + 1 else likes - 1
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Me gusta",
                        tint = if (meGusta) Color.Red else Color.Gray
                    )
                }

                // AnimatedContent anima cada vez que cambia "likes"
                AnimatedContent(
                    targetState = likes,
                    transitionSpec = {
                        if (targetState > initialState) {
                            // Sube: el numero nuevo entra desde abajo
                            (slideInVertically { alto -> alto } + fadeIn()) togetherWith
                                (slideOutVertically { alto -> -alto } + fadeOut())
                        } else {
                            // Baja: el numero nuevo entra desde arriba
                            (slideInVertically { alto -> -alto } + fadeIn()) togetherWith
                                (slideOutVertically { alto -> alto } + fadeOut())
                        }
                    },
                    label = "likes"
                ) { valor ->
                    Text(
                        text = "$valor likes",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

@Composable
private fun BotonSection() {
    // interactionSource le avisa a Compose si el dedo esta apretando el boton
    val interactionSource = remember { MutableInteractionSource() }
    val presionado by interactionSource.collectIsPressedAsState()

    // Si esta presionado el tamano baja a 0.9, si no vuelve a 1 (con rebote)
    val escala by animateFloatAsState(
        targetValue = if (presionado) 0.9f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "escala"
    )

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "4. Boton que se encoge",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Manten apretado el boton y mira como se achica.",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = { },
                interactionSource = interactionSource,
                modifier = Modifier.graphicsLayer {
                    scaleX = escala
                    scaleY = escala
                }
            ) {
                Text("Tocame")
            }
        }
    }
}

@Composable
private fun ListaSection() {
    // Estado: la lista se muestra o se oculta
    var mostrar by remember { mutableStateOf(false) }

    val canciones = listOf(
        "Cancion 1",
        "Cancion 2",
        "Cancion 3",
        "Cancion 4",
        "Cancion 5"
    )

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "5. Lista escalonada",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Las tarjetas entran una tras otra, como una playlist.",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(onClick = { mostrar = !mostrar }) {
                Text(if (mostrar) "Ocultar lista" else "Mostrar lista")
            }

            canciones.forEachIndexed { indice, nombre ->
                // Cada tarjeta espera un poquito mas que la anterior (150 ms por posicion)
                val espera = indice * 150

                AnimatedVisibility(
                    visible = mostrar,
                    enter = fadeIn(animationSpec = tween(durationMillis = 400, delayMillis = espera)) +
                        slideInVertically(
                            animationSpec = tween(durationMillis = 400, delayMillis = espera),
                            initialOffsetY = { alto -> alto / 2 }
                        ),
                    exit = fadeOut(animationSpec = tween(durationMillis = 200))
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(
                            text = nombre,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaSection() {
    // Estado: la tarjeta esta abierta o cerrada
    var expandida by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            // animateContentSize anima el cambio de tamano cuando el contenido cambia
            .animateContentSize()
            .clickable { expandida = !expandida }
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "6. Tarjeta que se expande",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Toca la tarjeta para ver mas o ver menos.",
                style = MaterialTheme.typography.bodyMedium
            )

            // Solo se muestra cuando la tarjeta esta expandida
            if (expandida) {
                Text(
                    text = "Este es el texto completo. Cuando tocas la tarjeta, " +
                        "su tamano cambia y Compose lo anima solo: la tarjeta " +
                        "crece suavemente en vez de saltar de golpe. Es lo mismo " +
                        "que pasa en el ver mas de Facebook o Instagram.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                text = if (expandida) "Ver menos" else "Ver mas",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun ProgresoSection() {
    // Estado: la descarga esta completa o vacia
    var completa by remember { mutableStateOf(false) }

    // Numero de 0 a 1 que Compose va moviendo solo durante 3 segundos
    val progreso by animateFloatAsState(
        targetValue = if (completa) 1f else 0f,
        animationSpec = tween(durationMillis = 3000),
        label = "progreso"
    )

    // El mismo numero, pero de 0 a 100 para mostrarlo como porcentaje
    val porcentaje = (progreso * 100).toInt()

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "7. Progreso animado",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "El circulo se llena y el numero sube, como una descarga.",
                style = MaterialTheme.typography.bodyMedium
            )

            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progreso },
                    modifier = Modifier.size(120.dp),
                    strokeWidth = 10.dp
                )
                Text(
                    text = "$porcentaje%",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(onClick = { completa = !completa }) {
                Text(if (completa) "Reiniciar" else "Descargar")
            }
        }
    }
}