package edu.ucb.project.animations.presentation.screen

import androidx.compose.ui.text.style.TextAlign
import edu.ucb.project.core.theme.colorTituloCabecera
import edu.ucb.project.core.theme.PantallaDegradado
import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun AnimationsScreen(navController: NavHostController) {
    PantallaDegradado(
        anclada = false,
        onVolver = { navController.popBackStack() },
        espacio = 24.dp,
        alineacion = Alignment.Start,
        cabecera = {
            Text(
                text = stringResource(Res.string.profile_animations),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = colorTituloCabecera(),
                textAlign = TextAlign.Center
            )
        },
        contenido = {
            // Aviso: esta es la version sin animaciones
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    text = stringResource(Res.string.anim_off_banner),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        // Animaciones 1 y 2: solo informativas, se ven en otras partes de la app
        InfoSection(
            titulo = stringResource(Res.string.anim_1_title),
            descripcion = stringResource(Res.string.anim_1_desc),
            donde = stringResource(Res.string.anim_1_where)
        )

        InfoSection(
            titulo = stringResource(Res.string.anim_2_title),
            descripcion = stringResource(Res.string.anim_2_desc),
            donde = stringResource(Res.string.anim_2_where)
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

        // Animacion 10: logo wifi (sin brillo)
        WifiSection()
        }
    )
}

@Composable
private fun LikesSection() {
    // Estados: cuando cambian, Compose anima solo
    var meGusta by remember { mutableStateOf(false) }
    var likes by remember { mutableStateOf(128) }

    // El color del corazon cambia suave de gris a rojo (y al reves)
    val colorCorazon by animateColorAsState(
        targetValue = if (meGusta) Color.Red else Color.Gray,
        animationSpec = tween(durationMillis = 0),
        label = "colorCorazon"
    )

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.anim_3_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(Res.string.anim_3_desc),
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(Res.string.anim_3_like),
                    tint = colorCorazon,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            meGusta = !meGusta
                            likes = if (meGusta) likes + 1 else likes - 1
                        }
                )

                // Sin animacion: el numero cambia de golpe
                Text(
                    text = stringResource(Res.string.anim_likes, likes),
                    style = MaterialTheme.typography.headlineSmall
                )
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
        targetValue = 1f,
        animationSpec = tween(durationMillis = 0),
        label = "escala"
    )

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.anim_4_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(Res.string.anim_4_desc),
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
                Text(stringResource(Res.string.anim_4_button))
            }
        }
    }
}

@Composable
private fun ListaSection() {
    // Estado: la lista se muestra o se oculta
    var mostrar by remember { mutableStateOf(false) }

    val canciones = listOf(
        stringResource(Res.string.anim_song, 1),
        stringResource(Res.string.anim_song, 2),
        stringResource(Res.string.anim_song, 3),
        stringResource(Res.string.anim_song, 4),
        stringResource(Res.string.anim_song, 5)
    )

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.anim_5_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(Res.string.anim_5_desc),
                style = MaterialTheme.typography.bodyMedium
            )

            Button(onClick = { mostrar = !mostrar }) {
                Text(if (mostrar) stringResource(Res.string.anim_5_hide) else stringResource(Res.string.anim_5_show))
            }

            canciones.forEachIndexed { indice, nombre ->
                // Cada tarjeta espera un poquito mas que la anterior (150 ms por posicion)
                val espera = indice * 150

                AnimatedVisibility(
                    visible = mostrar,
                    enter = fadeIn(animationSpec = tween(durationMillis = 0, delayMillis = 0)) +
                        slideInVertically(
                            animationSpec = tween(durationMillis = 0, delayMillis = 0),
                            initialOffsetY = { alto -> alto / 2 }
                        ),
                    exit = fadeOut(animationSpec = tween(durationMillis = 0))
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
            
            .clickable { expandida = !expandida },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.anim_6_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(Res.string.anim_6_desc),
                style = MaterialTheme.typography.bodyMedium
            )

            // Solo se muestra cuando la tarjeta esta expandida
            if (expandida) {
                Text(
                    text = stringResource(Res.string.anim_6_text),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                text = if (expandida) stringResource(Res.string.anim_6_less) else stringResource(Res.string.anim_6_more),
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
        animationSpec = tween(durationMillis = 0),
        label = "progreso"
    )

    // El mismo numero, pero de 0 a 100 para mostrarlo como porcentaje
    val porcentaje = (progreso * 100).toInt()

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.anim_7_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(Res.string.anim_7_desc),
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
                Text(if (completa) stringResource(Res.string.anim_7_reset) else stringResource(Res.string.anim_7_download))
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

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.anim_8_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(Res.string.anim_8_desc),
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
                Text(stringResource(Res.string.anim_8_simulate))
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
        targetValue = 0f,
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
                text = stringResource(Res.string.anim_profile_banner),
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
                    contentDescription = stringResource(Res.string.anim_profile_photo)
                )
            }
            Column {
                Text(
                    text = "Nazaret Claure",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(Res.string.anim_profile_career),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // El texto
        Text(
            text = stringResource(Res.string.anim_profile_bio),
            style = MaterialTheme.typography.bodyMedium
        )

        // Los botones
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { }) { Text(stringResource(Res.string.anim_follow)) }
            OutlinedButton(onClick = { }) { Text(stringResource(Res.string.anim_message)) }
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
        animationSpec = tween(durationMillis = 0),
        label = "tamano"
    )

    // Y a la vez gira de 0 a 360 grados
    val giro by animateFloatAsState(
        targetValue = if (grande) 360f else 0f,
        animationSpec = tween(durationMillis = 0),
        label = "giro"
    )

    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.anim_9_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(Res.string.anim_9_desc),
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
                    contentDescription = stringResource(Res.string.anim_9_cd),
                    modifier = Modifier
                        .size(tamano)
                        .rotate(giro)
                )
            }

            val letra by animateFloatAsState(
                targetValue = if (grande) 36f else 16f,
                animationSpec = tween(durationMillis = 0),
                label = "letra"
            )
            Text(
                text = stringResource(Res.string.anim_9_text),
                fontSize = letra.sp,
                fontWeight = FontWeight.Bold
            )

            Button(onClick = { grande = !grande }) {
                Text(if (grande) stringResource(Res.string.anim_9_shrink) else stringResource(Res.string.anim_9_grow))
            }
        }
    }
}
@Composable
private fun InfoSection(titulo: String, descripcion: String, donde: String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = donde,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun WifiSection() {
    // Version sin animacion: el logo se dibuja quieto, sin brillo
    val colorLogo = MaterialTheme.colorScheme.primary
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
                    val centro = Offset(size.width / 2, size.height * 0.85f)
                    val grosor = size.width * 0.07f

                    drawCircle(
                        color = colorLogo,
                        radius = size.width * 0.06f,
                        center = centro
                    )

                    val radios = listOf(0.22f, 0.38f, 0.54f)
                    for (i in 0..2) {
                        val r = size.width * radios[i]
                        drawArc(
                            color = colorLogo,
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
