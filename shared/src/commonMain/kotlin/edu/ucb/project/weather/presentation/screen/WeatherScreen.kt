package edu.ucb.project.weather.presentation.screen

import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucb.project.core.theme.PantallaHoja
import edu.ucb.project.weather.presentation.state.WeatherEffects
import edu.ucb.project.weather.presentation.state.WeatherEvents
import edu.ucb.project.weather.presentation.state.WeatherVM
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WeatherScreen(
    navController: NavHostController,
    viewModel: WeatherVM = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var lastError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is WeatherEffects.ShowError -> {
                    lastError = effect.message
                }
            }
        }
    }

    val hasResult = state.temperature != null

    PantallaHoja(
        titulo = stringResource(Res.string.weather_title),
        onVolver = { navController.popBackStack() },
        desplazable = true,
        espacio = 12.dp
    ) {
        OutlinedTextField(
            value = state.latitudeInput,
            onValueChange = { viewModel.onEvent(WeatherEvents.OnLatitudeChange(it)) },
            label = { Text(stringResource(Res.string.weather_latitude)) },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.longitudeInput,
            onValueChange = { viewModel.onEvent(WeatherEvents.OnLongitudeChange(it)) },
            label = { Text(stringResource(Res.string.weather_longitude)) },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                lastError = null
                viewModel.onEvent(WeatherEvents.Search)
            },
            enabled = state.latitudeInput.isNotBlank() && state.longitudeInput.isNotBlank() && !state.isLoading,
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text(stringResource(Res.string.common_search))
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (hasResult) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InfoRow(label = stringResource(Res.string.weather_info_temperature), value = "${state.temperature} \u00b0C")
                    InfoRow(label = stringResource(Res.string.weather_info_wind), value = "${state.windspeed} km/h")
                    InfoRow(label = stringResource(Res.string.weather_info_wind_direction), value = "${state.winddirection}\u00b0")
                    InfoRow(label = stringResource(Res.string.weather_info_code), value = "${state.weathercode}")
                    InfoRow(label = stringResource(Res.string.weather_info_datetime), value = "${state.time}")
                    InfoRow(label = stringResource(Res.string.weather_info_latitude), value = "${state.latitude}")
                    InfoRow(label = stringResource(Res.string.weather_info_longitude), value = "${state.longitude}")
                }
            }
        }

        lastError?.let { message ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
//composable conecta basicamente lo anterior