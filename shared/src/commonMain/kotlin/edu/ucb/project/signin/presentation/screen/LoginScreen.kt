package edu.ucb.project.signin.presentation.screen

import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucb.ds.components.buttons.PrimaryButton
import edu.ucb.ds.components.cards.DsCard
import edu.ucb.ds.components.labels.DsLabel
import edu.ucb.ds.components.textfields.DsTextField
import edu.ucb.ds.theme.DsTheme
import edu.ucb.project.core.navigation.NavRoute
import edu.ucb.project.signin.presentation.state.SignInEffects
import edu.ucb.project.signin.presentation.state.SignInEvents
import edu.ucb.project.signin.presentation.state.SignInVM
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: SignInVM = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var isPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is SignInEffects.NavigateToMovies -> {
                    navController.navigate(NavRoute.Movies) {
                        popUpTo(NavRoute.Login) { inclusive = true }
                    }
                }
                is SignInEffects.ShowError -> println("ERROR: ${effect.message}")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.login_welcome),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        DsLabel(
            text = stringResource(Res.string.login_subtitle),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        DsTextField(
            value = state.email,
            onValueChange = { viewModel.onEvent(SignInEvents.OnEmailChanged(it)) },
            label = stringResource(Res.string.common_email),
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        DsTextField(
            value = state.pass,
            onValueChange = { viewModel.onEvent(SignInEvents.OnPasswordChanged(it)) },
            label = stringResource(Res.string.common_password),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (isPasswordVisible) stringResource(Res.string.login_hide_password) else stringResource(Res.string.login_show_password)
                    )
                }
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )

        if (state.error != null) {
            DsCard(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                DsLabel(
                    text = stringResource(Res.string.common_error, state.error.toString()),
                    color = DsTheme.colors.error
                )
            }
        }

        PrimaryButton(
            text = stringResource(Res.string.login_button),
            onClick = { viewModel.onEvent(SignInEvents.OnSubmit) },
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
        )
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
        TextButton(onClick = { navController.navigate(NavRoute.SignUp) }) {
            Text(stringResource(Res.string.login_no_account))
        }
    }
}