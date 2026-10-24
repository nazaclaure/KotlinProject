package edu.ucb.project.signup.presentation.screen

import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucb.project.signup.presentation.state.SignUpEffects
import edu.ucb.project.signup.presentation.state.SignUpEvents
import edu.ucb.project.signup.presentation.state.SignUpVM
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpVM = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is SignUpEffects.NavigateToLogin -> navController.popBackStack()
                is SignUpEffects.ShowError -> println("ERROR: ${effect.message}")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextButton(onClick = { navController.popBackStack() }) {
            Text(stringResource(Res.string.common_back))
        }

        Text(
            text = stringResource(Res.string.signup_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = state.name,
            onValueChange = { viewModel.onEvent(SignUpEvents.OnNameChanged(it)) },
            label = { Text(stringResource(Res.string.signup_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEvent(SignUpEvents.OnEmailChanged(it)) },
            label = { Text(stringResource(Res.string.common_email)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.pass,
            onValueChange = { viewModel.onEvent(SignUpEvents.OnPasswordChanged(it)) },
            label = { Text(stringResource(Res.string.common_password)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (state.error != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = stringResource(Res.string.common_error, state.error.toString()),
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Button(
            onClick = { viewModel.onEvent(SignUpEvents.OnRegisterSubmitted) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(4.dp))
            } else {
                Text(stringResource(Res.string.signup_button))
            }
        }
    }
}
