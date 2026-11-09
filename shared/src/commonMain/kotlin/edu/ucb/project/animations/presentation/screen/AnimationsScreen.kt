package edu.ucb.project.animations.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
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