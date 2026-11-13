package edu.ucb.project.movies.presentation.screen

import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucb.ds.components.buttons.PrimaryButton
import edu.ucb.project.analytics.AnalyticsTracker
import edu.ucb.project.core.navigation.NavRoute
import edu.ucb.project.core.notifications.LocalNotifier
import edu.ucb.project.core.survey.SurveyConfig
import edu.ucb.project.core.theme.PantallaDegradado
import edu.ucb.project.core.theme.colorSubtituloCabecera
import edu.ucb.project.core.theme.colorTituloCabecera
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

    PantallaDegradado(
        anclada = false,
        espacio = 14.dp,
        cabecera = {
            // Perfil arriba a la derecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FilledTonalButton(
                    onClick = { navController.navigate(NavRoute.Profile) },
                    shape = RoundedCornerShape(50)
                ) {
                    Text(stringResource(Res.string.movies_profile))
                }
            }

            Text(
                text = stringResource(Res.string.movies_title),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = colorTituloCabecera(),
                textAlign = TextAlign.Center
            )
            // Mensaje que viene de Remote Config
            Text(
                text = config.mensajeBienvenida,
                style = MaterialTheme.typography.bodyLarge,
                color = colorSubtituloCabecera(),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )

            // Peliculas: juntas con el titulo
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.error != null -> {
                    Text(
                        text = stringResource(Res.string.common_error, state.error.toString()),
                        color = colorTituloCabecera()
                    )
                }
                else -> {
                    state.movies.forEach { movie ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                                .clickable {
                                    // ANALYTICS: el usuario toco una pelicula
                                    analytics.logEvent(
                                        "movie_click",
                                        mapOf("movie_id" to movie.id.toString())
                                    )
                                    viewModel.onEvent(MovieEvents.OnMovieClicked(movie.id))
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
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

            // Catalogo va junto a las peliculas
            FilledTonalButton(
                onClick = { navController.navigate(NavRoute.Catalog) },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text(stringResource(Res.string.movies_catalog))
            }
        },
        contenido = {
            // E25-5: boton que dispara una notificacion local
            PrimaryButton(
                text = stringResource(Res.string.movies_notify),
                onClick = { LocalNotifier.show(notifyTitle, notifyText) },
                modifier = Modifier.fillMaxWidth()
            )

            // Promo: solo se muestra si Remote Config lo activa
            if (config.mostrarPromo) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
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

            // Separacion para no confundir estos botones con las peliculas
            Spacer(modifier = Modifier.height(20.dp))

            FilledTonalButton(
                onClick = { navController.navigate(NavRoute.Weather) },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text(stringResource(Res.string.movies_weather))
            }
            FilledTonalButton(
                onClick = { navController.navigate(NavRoute.Dollar) },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text(stringResource(Res.string.movies_dollar))
            }
            // SURVEYMONKEY: boton fijo para abrir la encuesta cuando el usuario quiera
            FilledTonalButton(
                onClick = {
                    analytics.logEvent("open_survey", mapOf("source" to "button"))
                    uriHandler.openUri(SurveyConfig.SURVEY_URL)
                },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text(stringResource(Res.string.movies_feedback))
            }
        }
    )
}