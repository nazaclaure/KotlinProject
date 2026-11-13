package edu.ucb.project.userinformation.presentation.screen

import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import edu.ucb.project.core.navigation.NavRoute
import edu.ucb.project.core.theme.PantallaHoja
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

    PantallaHoja(
        titulo = stringResource(Res.string.profile_github),
        onVolver = { viewModel.emitEvent(UserInformationEvent.OnBack) },
        desplazable = true,
        espacio = 12.dp
    ) {
        OutlinedTextField(
            value = state.alias,
            onValueChange = { viewModel.emitEvent(UserInformationEvent.OnAliasChange(it)) },
            label = { Text(stringResource(Res.string.github_user)) },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                lastToast = null
                viewModel.emitEvent(UserInformationEvent.OnSubmit)
            },
            enabled = state.alias.isNotBlank() && !state.isLoading,
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
                    Text(
                        text = state.alias,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (!state.avatarUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = state.avatarUrl,
                            contentDescription = state.alias,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    }
                    if (!state.email.isNullOrEmpty()) {
                        InfoRow(label = stringResource(Res.string.userinfo_email), value = state.email!!)
                    }
                    if (!state.company.isNullOrEmpty()) {
                        InfoRow(label = stringResource(Res.string.userinfo_company), value = state.company!!)
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