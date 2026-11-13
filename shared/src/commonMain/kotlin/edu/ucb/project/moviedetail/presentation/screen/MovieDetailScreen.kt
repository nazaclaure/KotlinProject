package edu.ucb.project.moviedetail.presentation.screen

import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucb.project.core.theme.PantallaHoja
import edu.ucb.project.moviedetail.presentation.state.MovieDetailEffects
import edu.ucb.project.moviedetail.presentation.state.MovieDetailEvents
import edu.ucb.project.moviedetail.presentation.state.MovieDetailVM
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailScreen(
    movieId: String,
    navController: NavHostController,
    viewModel: MovieDetailVM = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.onEvent(MovieDetailEvents.LoadDetail(movieId))
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is MovieDetailEffects.NavigateBack -> navController.popBackStack()
            }
        }
    }

    PantallaHoja(
        onVolver = { viewModel.onEvent(MovieDetailEvents.OnBackClicked) },
        desplazable = true,
        espacio = 12.dp
    ) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                Text(text = stringResource(Res.string.common_error, state.error.toString()))
            }
            state.detail != null -> {
                Text(
                    text = state.detail!!.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = state.detail!!.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}