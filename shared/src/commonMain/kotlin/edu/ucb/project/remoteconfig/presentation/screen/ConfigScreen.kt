package edu.ucb.project.remoteconfig.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucb.ds.components.buttons.PrimaryButton
import edu.ucb.ds.components.cards.DsCard
import edu.ucb.ds.components.labels.DsLabel
import edu.ucb.project.core.theme.PantallaHoja
import edu.ucb.project.remoteconfig.presentation.state.ConfigEvents
import edu.ucb.project.remoteconfig.presentation.state.ConfigVM
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConfigScreen(
    viewModel: ConfigVM = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ConfigEvents.LoadConfig)
    }

    PantallaHoja(
        titulo = "Configuraci\u00f3n remota",
        desplazable = true,
        espacio = 12.dp
    ) {
        if (state.isOffline) {
            DsLabel(
                text = "offline"
            )
        }

        DsCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                DsLabel(
                    text = "Mensaje: ${state.welcomeMessage}"
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                DsLabel(
                    text = "M\u00e1ximo de elementos: ${state.maxItems}"
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                DsLabel(
                    text = "Feature habilitado: ${state.featureEnabled}"
                )
            }
        }

        if (state.isLoading) {
            DsLabel(
                text = "Cargando..."
            )
        }

        state.error?.let {
            DsLabel(
                text = "Error: $it"
            )
        }

        PrimaryButton(
            text = "Refrescar",
            onClick = {
                viewModel.onEvent(
                    ConfigEvents.RefreshConfig
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}