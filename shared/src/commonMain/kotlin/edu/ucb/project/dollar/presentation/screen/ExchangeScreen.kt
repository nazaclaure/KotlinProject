package edu.ucb.project.dollar.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucb.ds.components.buttons.PrimaryButton
import edu.ucb.ds.icons.UserIcon
import edu.ucb.ds.theme.DsTheme
import edu.ucb.project.dollar.presentation.state.ExchangeVM
import kotlinproject.shared.generated.resources.Res
import kotlinproject.shared.generated.resources.exchange_back
import kotlinproject.shared.generated.resources.exchange_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExchangeScreen(
    navController: NavHostController,
    viewModel: ExchangeVM = koinViewModel()
) {
    val message = viewModel.message.collectAsState()

    DsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(20.dp)
        ) {
            PrimaryButton(
                text = stringResource(Res.string.exchange_back),
                onClick = { navController.popBackStack() }
            )

            Box(
                modifier = Modifier.weight(1f).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = UserIcon,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = DsTheme.colors.primary
                    )
                    Text(
                        text = stringResource(Res.string.exchange_title),
                        style = DsTheme.typography.heading
                    )
                    Text(
                        text = message.value,
                        style = DsTheme.typography.body
                    )
                }
            }
        }
    }
}