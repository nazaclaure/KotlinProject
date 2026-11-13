package edu.ucb.project.remoteconfig.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*
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
        titulo = stringResource(Res.string.profile_remote_config),
        desplazable = true,
        espacio = 12.dp
    ) {
        if (state.isOffline) {
            DsLabel(
                text = stringResource(Res.string.config_offline)
            )
        }

        DsCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                DsLabel(
                    text = stringResource(Res.string.config_message, state.welcomeMessage)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                DsLabel(
                    text = stringResource(Res.string.config_max_items, state.maxItems)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                DsLabel(
                    text = stringResource(Res.string.config_feature, state.featureEnabled)
                )
            }
        }

        if (state.isLoading) {
            DsLabel(
                text = stringResource(Res.string.config_loading)
            )
        }

        state.error?.let {
            DsLabel(
                text = stringResource(Res.string.config_error, it)
            )
        }

        PrimaryButton(
            text = stringResource(Res.string.config_refresh),
            onClick = {
                viewModel.onEvent(
                    ConfigEvents.RefreshConfig
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}