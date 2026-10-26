package edu.ucb.project.remoteconfig.presentation.state

data class ConfigState(
    val welcomeMessage: String = "",
    val maxItems: Long = 0,
    val featureEnabled: Boolean = false,
    val isOffline: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)