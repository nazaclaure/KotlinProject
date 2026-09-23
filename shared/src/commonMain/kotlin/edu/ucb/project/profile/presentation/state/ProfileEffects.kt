package edu.ucb.project.profile.presentation.state

sealed interface ProfileEffects {
    data object NavigateToLogin : ProfileEffects
}
