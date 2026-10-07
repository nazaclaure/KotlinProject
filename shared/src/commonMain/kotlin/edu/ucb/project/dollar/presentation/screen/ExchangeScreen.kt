package edu.ucb.project.dollar.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucb.project.dollar.presentation.state.ExchangeVM
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExchangeScreen(
    navController: NavHostController, // NUEVO
    viewModel: ExchangeVM = koinViewModel()
) {
    val message = viewModel.message.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(20.dp)
    ) {
        TextButton(onClick = { navController.popBackStack() }) { // NUEVO
            Text("< Volver")
        }

        Box(
            modifier = Modifier.weight(1f).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(message.value)
        }
    }
}