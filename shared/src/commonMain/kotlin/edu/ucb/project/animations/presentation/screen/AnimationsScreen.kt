package edu.ucb.project.animations.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinproject.shared.generated.resources.Res
import kotlinproject.shared.generated.resources.corazon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

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

        // Animacion 8: brillo de carga (skeleton)
        SkeletonSection()

        // Animacion 9: vector animado
        VectorSection()
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

@Composable
private fun SkeletonSection() {
    // Estado: mostrando el esqueleto (cargando) o el contenido real
    var cargando by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Al abrir la pantalla tambien simula una carga de 3 segundos
    LaunchedEffect(Unit) {
        delay(3000)
        cargando = false
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "8. Brillo de carga (skeleton)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Mientras cargan los datos se ven rectangulos grises con una luz que pasa.",
                style = MaterialTheme.typography.bodyMedium
            )

            if (cargando) {
                PerfilEsqueleto()
            } else {
                PerfilReal()
            }

            Button(
                onClick = {
                    scope.launch {
                        cargando = true
                        delay(3000)
                        cargando = false
                    }
                }
            ) {
                Text("Simular carga")
            }
        }
    }
}

// El brillo: un degradado que se corre de lado. Lo comparten todos los recuadros
@Composable
private fun rememberBrillo(): Brush {
    // Reloj infinito: un numero que va de 0 a 1500 una y otra vez
    val transicion = rememberInfiniteTransition(label = "brillo")
    val desplazamiento by transicion.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "desplazamiento"
    )

    // Degradado gris oscuro, gris claro, gris oscuro
    return Brush.linearGradient(
        colors = listOf(
            Color(0xFFD6D6D6),
            Color(0xFFF2F2F2),
            Color(0xFFD6D6D6)
        ),
        start = Offset(desplazamiento - 400f, 0f),
        end = Offset(desplazamiento, 0f)
    )
}

// El esqueleto: mismas formas que el contenido real, pero grises y con brillo
@Composable
private fun PerfilEsqueleto() {
    val brillo = rememberBrillo()

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // Recuadro grande: la foto de la publicacion
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(brillo)
        )

        // Circulo + dos lineas: foto de perfil, nombre y carrera
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(brillo)
            )
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(brillo)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(brillo)
                )
            }
        }

        // Dos lineas de texto
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(brillo)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(14.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(brillo)
        )

        // Dos botones
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .size(width = 90.dp, height = 36.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(brillo)
            )
            Box(
                modifier = Modifier
                    .size(width = 90.dp, height = 36.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(brillo)
            )
        }
    }
}

// El contenido real que aparece cuando termina la carga (mismas formas que el esqueleto)
@Composable
private fun PerfilReal() {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // La foto de la publicacion
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Animaciones en Compose",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Foto de perfil, nombre y carrera
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Foto de perfil"
                )
            }
            Column {
                Text(
                    text = "Nazaret Claure",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Ingenieria de Sistemas",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // El texto
        Text(
            text = "Estoy aprendiendo animaciones en Compose. Todo lo que ves se anima cambiando un estado.",
            style = MaterialTheme.typography.bodyMedium
        )

        // Los botones
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { }) { Text("Seguir") }
            OutlinedButton(onClick = { }) { Text("Mensaje") }
        }
    }
}
@Composable
private fun VectorSection() {
    // Estado: el corazon esta grande o chico
    var grande by remember { mutableStateOf(false) }

    // Compose mueve el tamano de 60.dp a 200.dp durante 1 segundo
    val tamano by animateDpAsState(
        targetValue = if (grande) 200.dp else 60.dp,
        animationSpec = tween(durationMillis = 1000),
        label = "tamano"
    )

    // Y a la vez gira de 0 a 360 grados
    val giro by animateFloatAsState(
        targetValue = if (grande) 360f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "giro"
    )

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "9. Vector animado (SVG)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "El corazon es una imagen vectorial: crece y gira, y se ve nitido todo el tiempo.",
                style = MaterialTheme.typography.bodyMedium
            )

            // Caja fija para que el resto de la pantalla no salte cuando crece
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.corazon),
                    contentDescription = "Corazon vectorial",
                    modifier = Modifier
                        .size(tamano)
                        .rotate(giro)
                )
            }

            val letra by animateFloatAsState(
                targetValue = if (grande) 36f else 16f,
                animationSpec = tween(durationMillis = 1000),
                label = "letra"
            )
            Text(
                text = "Texto vectorial",
                fontSize = letra.sp,
                fontWeight = FontWeight.Bold
            )

            Button(onClick = { grande = !grande }) {
                Text(if (grande) "Achicar" else "Agrandar")
            }
        }
    }
}