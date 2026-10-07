package edu.ucb.project.dollar.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import edu.ucb.project.core.navigation.NavRoute // NUEVO
import edu.ucb.project.dollar.presentation.state.DollarEffects
import edu.ucb.project.dollar.presentation.state.DollarEvents
import edu.ucb.project.dollar.presentation.state.DollarVM
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DollarScreen(
    navController: NavHostController,
    viewModel: DollarVM = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var lastError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is DollarEffects.ShowError -> {
                    lastError = effect.message
                }
            }
        }
    }

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
            text = "Cambio de Dolar",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = state.officialInput,
            onValueChange = { viewModel.onEvent(DollarEvents.OnOfficialChange(it)) },
            label = { Text("Tasa oficial") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.parallelInput,
            onValueChange = { viewModel.onEvent(DollarEvents.OnParallelChange(it)) },
            label = { Text("Tasa paralela") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                lastError = null
                viewModel.onEvent(DollarEvents.OnAddRecord)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar")
        }

        Button( // NUEVO
            onClick = { navController.navigate(NavRoute.Exchange) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dolar en vivo (Firebase)")
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.list) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Oficial: ${item.dollarOfficial}")
                        Text("Paralelo: ${item.dollarParallel}")
                    }
                }
            }
        }
    }
}