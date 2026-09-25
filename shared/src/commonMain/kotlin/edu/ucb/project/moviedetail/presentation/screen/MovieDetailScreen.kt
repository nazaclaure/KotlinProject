package edu.ucb.project.moviedetail.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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

    Box(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error != null -> {
                Text(
                    text = "Error: ${state.error}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.detail != null -> {
                Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                    TextButton(onClick = { viewModel.onEvent(MovieDetailEvents.OnBackClicked) }) {
                        Text("< Volver")
                    }
                    Text(
                        text = state.detail!!.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                    )
                    Text(
                        text = state.detail!!.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
