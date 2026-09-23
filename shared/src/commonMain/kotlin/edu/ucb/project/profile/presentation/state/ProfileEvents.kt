package edu.ucb.project.profile.presentation.state

sealed interface ProfileEvents {
    data object LoadProfile : ProfileEvents
    data object OnLogout : ProfileEvents
}
