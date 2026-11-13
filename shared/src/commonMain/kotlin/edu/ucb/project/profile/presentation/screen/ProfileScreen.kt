package edu.ucb.project.profile.presentation.screen

import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucb.project.core.navigation.NavRoute
import edu.ucb.project.core.theme.AppearanceSettings
import edu.ucb.project.core.theme.PantallaDegradado
import edu.ucb.project.core.theme.colorSubtituloCabecera
import edu.ucb.project.core.theme.colorTituloCabecera
import edu.ucb.project.profile.presentation.state.ProfileEffects
import edu.ucb.project.profile.presentation.state.ProfileEvents
import edu.ucb.project.profile.presentation.state.ProfileVM
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileVM = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProfileEvents.LoadProfile)

        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ProfileEffects.NavigateToLogin -> {
                    navController.navigate(NavRoute.Login) {
                        popUpTo(0)
                    }
                }
            }
        }
    }

    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(Res.string.common_error, state.error.toString())
                )
            }
        }

        state.profile != null -> {
            PantallaDegradado(
                anclada = false,
                onVolver = { navController.popBackStack() },
                espacio = 12.dp,
                cabecera = {
                    // Foto con la inicial, nombre y correo
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.profile!!.name.take(1).uppercase(),
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF7B57C9)
                            )
                        }
                        Text(
                            text = state.profile!!.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = colorTituloCabecera(),
                            modifier = Modifier.padding(top = 14.dp)
                        )
                        Text(
                            text = state.profile!!.email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorSubtituloCabecera()
                        )
                    }
                },
                contenido = {
                    OpcionPerfil(
                        icono = Icons.Default.Search,
                        texto = stringResource(Res.string.profile_github),
                        onClick = { navController.navigate(NavRoute.UserInformation) }
                    )
                    OpcionPerfil(
                        icono = Icons.Default.Settings,
                        texto = stringResource(Res.string.profile_remote_config),
                        onClick = { navController.navigate(NavRoute.Config) }
                    )
                    OpcionPerfil(
                        icono = Icons.Default.Favorite,
                        texto = stringResource(Res.string.profile_animations),
                        onClick = { navController.navigate(NavRoute.Animations) }
                    )

                    // Ajustes de apariencia dentro de una tarjeta suave
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        AppearanceSettings()
                    }

                    Button(
                        onClick = { viewModel.onEvent(ProfileEvents.OnLogout) },
                        shape = RoundedCornerShape(28.dp),
                        modifier = Modifier.fillMaxWidth().height(52.dp)
                    ) {
                        Text(stringResource(Res.string.profile_logout))
                    }
                }
            )
        }
    }
}

// Una fila del menu: icono en circulo lila, texto y flecha
@Composable
private fun OpcionPerfil(
    icono: ImageVector,
    texto: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = texto,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f).padding(start = 16.dp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}