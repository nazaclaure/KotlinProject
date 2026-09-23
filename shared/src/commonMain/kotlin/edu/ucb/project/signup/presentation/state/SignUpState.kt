package edu.ucb.project.signup.presentation.state

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val pass: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
