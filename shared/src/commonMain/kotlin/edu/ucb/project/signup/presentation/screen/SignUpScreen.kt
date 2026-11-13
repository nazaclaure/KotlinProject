package edu.ucb.project.signup.presentation.screen

import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucb.ds.components.buttons.PrimaryButton
import edu.ucb.ds.components.cards.DsCard
import edu.ucb.ds.components.labels.DsLabel
import edu.ucb.ds.components.textfields.DsTextField
import edu.ucb.ds.theme.DsTheme
import edu.ucb.project.core.theme.PantallaDegradado
import edu.ucb.project.core.theme.colorTituloCabecera
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

    PantallaDegradado(
        onVolver = { navController.popBackStack() },
        espacio = 16.dp,
        cabecera = {
            Text(
                text = stringResource(Res.string.signup_title),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = colorTituloCabecera(),
                textAlign = TextAlign.Center
            )
        },
        contenido = {
            DsTextField(
                value = state.name,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnNameChanged(it)) },
                label = stringResource(Res.string.signup_name),
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            DsTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnEmailChanged(it)) },
                label = stringResource(Res.string.common_email),
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            DsTextField(
                value = state.pass,
                onValueChange = { viewModel.onEvent(SignUpEvents.OnPasswordChanged(it)) },
                label = stringResource(Res.string.common_password),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (state.error != null) {
                DsCard(modifier = Modifier.fillMaxWidth()) {
                    DsLabel(
                        text = stringResource(Res.string.common_error, state.error.toString()),
                        color = DsTheme.colors.error
                    )
                }
            }

            PrimaryButton(
                text = stringResource(Res.string.signup_button),
                onClick = { viewModel.onEvent(SignUpEvents.OnRegisterSubmitted) },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    )
}