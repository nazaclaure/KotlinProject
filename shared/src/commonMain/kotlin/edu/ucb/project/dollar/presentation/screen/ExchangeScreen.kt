package edu.ucb.project.dollar.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucb.ds.icons.UserIcon
import edu.ucb.ds.theme.DsTheme
import edu.ucb.project.core.theme.PantallaHoja
import edu.ucb.project.dollar.presentation.state.ExchangeVM
import kotlinproject.shared.generated.resources.Res
import kotlinproject.shared.generated.resources.exchange_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExchangeScreen(
    navController: NavHostController,
    viewModel: ExchangeVM = koinViewModel()
) {
    val message = viewModel.message.collectAsState()

    PantallaHoja(
        titulo = stringResource(Res.string.exchange_title),
        onVolver = { navController.popBackStack() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = UserIcon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = DsTheme.colors.primary
            )
            Text(
                text = message.value,
                style = DsTheme.typography.body
            )
        }
    }
}