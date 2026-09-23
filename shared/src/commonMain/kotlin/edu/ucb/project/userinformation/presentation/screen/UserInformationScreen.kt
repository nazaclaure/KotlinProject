package edu.ucb.project.userinformation.presentation.screen

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
import org.koin.compose.viewmodel.koinViewModel
import edu.ucb.project.core.navigation.NavRoute
import edu.ucb.project.userinformation.presentation.viewmodel.UserInformationEffect
import edu.ucb.project.userinformation.presentation.viewmodel.UserInformationEvent
import edu.ucb.project.userinformation.presentation.viewmodel.UserInformationViewModel

@Composable
fun UserInformationScreen(
    navController: NavHostController,
    viewModel: UserInformationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var lastToast by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is UserInformationEffect.ShowToast -> {
                    lastToast = effect.message
                }
                UserInformationEffect.NavigateToHome -> {
                    navController.navigate(NavRoute.Movies)
                }
                UserInformationEffect.NavigateToBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    val hasResult = !state.email.isNullOrEmpty() || !state.company.isNullOrEmpty() || !state.avatarUrl.isNullOrEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(onClick = { viewModel.emitEvent(UserInformationEvent.OnBack) }) {
            Text("< Volver")
        }

        Text(
            text = "Buscar en GitHub",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = state.alias,
            onValueChange = { viewModel.emitEvent(UserInformationEvent.OnAliasChange(it)) },
            label = { Text("Usuario de GitHub") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                lastToast = null
                viewModel.emitEvent(UserInformationEvent.OnSubmit)
            },
            enabled = state.alias.isNotBlank() && !state.isLoading,
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
                    Text(
                        text = state.alias,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (!state.email.isNullOrEmpty()) {
                        InfoRow(label = "Email", value = state.email!!)
                    }
                    if (!state.company.isNullOrEmpty()) {
                        InfoRow(label = "Empresa", value = state.company!!)
                    }
                    if (!state.avatarUrl.isNullOrEmpty()) {
                        InfoRow(label = "Avatar", value = state.avatarUrl!!)
                    }
                }
            }
        }

        lastToast?.let { message ->
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