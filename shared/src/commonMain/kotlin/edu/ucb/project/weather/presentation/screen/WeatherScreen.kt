package edu.ucb.project.weather.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(onClick = { navController.popBackStack() }) {
            Text("< Volver")
        }

        Text(
            text = "Clima",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = state.latitudeInput,
            onValueChange = { viewModel.onEvent(WeatherEvents.OnLatitudeChange(it)) },
            label = { Text("Latitud") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.longitudeInput,
            onValueChange = { viewModel.onEvent(WeatherEvents.OnLongitudeChange(it)) },
            label = { Text("Longitud") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                lastError = null
                viewModel.onEvent(WeatherEvents.Search)
            },
            enabled = state.latitudeInput.isNotBlank() && state.longitudeInput.isNotBlank() && !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar")
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
                    InfoRow(label = "Temperatura", value = "${state.temperature} °C")
                    InfoRow(label = "Viento", value = "${state.windspeed} km/h")
                    InfoRow(label = "Direccion del viento", value = "${state.winddirection}°")
                    InfoRow(label = "Codigo de clima", value = "${state.weathercode}")
                    InfoRow(label = "Fecha y hora", value = "${state.time}")
                    InfoRow(label = "Latitud", value = "${state.latitude}")
                    InfoRow(label = "Longitud", value = "${state.longitude}")
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