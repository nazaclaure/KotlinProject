package edu.ucb.project.remoteconfig.presentation.state

sealed class ConfigEvents {
    data object LoadConfig : ConfigEvents()
    data object RefreshConfig : ConfigEvents()
}