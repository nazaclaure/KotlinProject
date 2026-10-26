package edu.ucb.project.movies.presentation.screen

import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucb.ds.components.buttons.PrimaryButton
import edu.ucb.project.analytics.AnalyticsTracker
import edu.ucb.project.core.navigation.NavRoute
import edu.ucb.project.core.notifications.LocalNotifier
import edu.ucb.project.core.survey.SurveyConfig
import edu.ucb.project.core.theme.LocalThemeController
import edu.ucb.project.core.theme.FontSizeChoice
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import edu.ucb.project.core.theme.ThemeChoice
import edu.ucb.project.movies.presentation.state.MovieEffects
import edu.ucb.project.movies.presentation.state.MovieEvents
import edu.ucb.project.movies.presentation.state.MovieVM
import edu.ucb.project.remoteconfig.domain.model.AppConfig
import edu.ucb.project.remoteconfig.domain.usecase.GetAppConfigUseCase
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieScreen(
    navController: NavHostController,
    viewModel: MovieVM = koinViewModel(),
    getAppConfig: GetAppConfigUseCase = koinInject(),
    analytics: AnalyticsTracker = koinInject()
) {
    val state by viewModel.uiState.collectAsState()
    val themeController = LocalThemeController.current
    val uriHandler = LocalUriHandler.current

    // Textos de la notificacion local (traducidos segun el idioma)
    val notifyTitle = stringResource(Res.string.notify_title)
    val notifyText = stringResource(Res.string.notify_text)

    // REMOTE CONFIG: valores remotos (mensaje de bienvenida y promo)
    var config by remember { mutableStateOf(AppConfig.DEFAULT) }

    // SURVEYMONKEY: invitacion a la encuesta
    var showSurveyPrompt by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        config = getAppConfig()
    }

    // ANALYTICS: el usuario abrio la pantalla de peliculas
    LaunchedEffect(Unit) {
        analytics.logEvent("view_movies", emptyMap())
    }

    // SURVEYMONKEY: a los 8 segundos, una sola vez por sesion, invita a responder la encuesta
    LaunchedEffect(Unit) {
        if (!SurveyConfig.promptShown) {
            delay(8_000)
            SurveyConfig.promptShown = true
            showSurveyPrompt = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(MovieEvents.LoadMovies)
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is MovieEffects.NavigateToDetail -> {
                    navController.navigate(NavRoute.MovieDetail(effect.movieId))
                }
            }
        }
    }

    if (showSurveyPrompt) {
        AlertDialog(
            onDismissRequest = { showSurveyPrompt = false },
            title = { Text(stringResource(Res.string.survey_title)) },
            text = { Text(stringResource(Res.string.survey_text)) },
            confirmButton = {
                TextButton(onClick = {
                    showSurveyPrompt = false
                    analytics.logEvent("open_survey", mapOf("source" to "prompt"))
                    uriHandler.openUri(SurveyConfig.SURVEY_URL)
                }) {
                    Text(stringResource(Res.string.survey_answer))
                }
            },
            dismissButton = {
                TextButton(onClick = { showSurveyPrompt = false }) {
                    Text(stringResource(Res.string.survey_later))
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(Res.string.movies_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { navController.navigate(NavRoute.Profile) }) {
                Text(stringResource(Res.string.movies_profile))
            }
        }

        // Mensaje que viene de Remote Config
        Text(
            text = config.mensajeBienvenida,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // E25-5: boton que dispara una notificacion local
        PrimaryButton(
            text = stringResource(Res.string.movies_notify),
            onClick = { LocalNotifier.show(notifyTitle, notifyText) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Promo: solo se muestra si Remote Config lo activa
        if (config.mostrarPromo) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = stringResource(Res.string.movies_promo),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null -> {
                    Text(
                        text = stringResource(Res.string.common_error, state.error.toString()),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.movies) { movie ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        // ANALYTICS: el usuario toco una pelicula
                                        analytics.logEvent(
                                            "movie_click",
                                            mapOf("movie_id" to movie.id.toString())
                                        )
                                        viewModel.onEvent(MovieEvents.OnMovieClicked(movie.id))
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Text(
                                    text = movie.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        TextButton(
            onClick = { navController.navigate(NavRoute.Catalog) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        ) {
            Text(stringResource(Res.string.movies_catalog))
        }
        TextButton(
            onClick = { navController.navigate(NavRoute.Weather) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        ) {
            Text(stringResource(Res.string.movies_weather))
        }
        TextButton(
            onClick = { navController.navigate(NavRoute.Dollar) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        ) {
            Text(stringResource(Res.string.movies_dollar))
        }
        // SURVEYMONKEY: boton fijo para abrir la encuesta cuando el usuario quiera
        TextButton(
            onClick = {
                analytics.logEvent("open_survey", mapOf("source" to "button"))
                uriHandler.openUri(SurveyConfig.SURVEY_URL)
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        ) {
            Text(stringResource(Res.string.movies_feedback))
        }
    }
}