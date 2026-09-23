package edu.ucb.project.profile.presentation.state

import edu.ucb.project.profile.domain.model.ProfileModel

data class ProfileState(
    val profile: ProfileModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
