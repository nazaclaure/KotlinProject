package edu.ucb.project.signup.presentation.state

sealed interface SignUpEvents {
    data class OnNameChanged(val name: String) : SignUpEvents
    data class OnEmailChanged(val email: String) : SignUpEvents
    data class OnPasswordChanged(val pass: String) : SignUpEvents
    data object OnRegisterSubmitted : SignUpEvents
}
