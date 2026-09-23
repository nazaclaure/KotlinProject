package edu.ucb.project.signin.presentation.state

sealed interface SignInEvents {
    data class OnEmailChanged(val email: String) : SignInEvents
    data class OnPasswordChanged(val pass: String) : SignInEvents
    data object OnSubmit : SignInEvents
}
