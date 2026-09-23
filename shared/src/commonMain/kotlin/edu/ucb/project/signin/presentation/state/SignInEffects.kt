package edu.ucb.project.signin.presentation.state

sealed interface SignInEffects {
    data object NavigateToMovies : SignInEffects
    data class ShowError(val message: String) : SignInEffects
}
