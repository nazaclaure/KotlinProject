package edu.ucb.project.signin.presentation.state

data class SignInState(
    val email: String = "",
    val pass: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
