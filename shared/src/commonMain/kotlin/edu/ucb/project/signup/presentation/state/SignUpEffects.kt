package edu.ucb.project.signup.presentation.state

sealed interface SignUpEffects {
    data object NavigateToLogin : SignUpEffects
    data class ShowError(val message: String) : SignUpEffects
}
