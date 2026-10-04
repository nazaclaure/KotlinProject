package edu.ucb.project.dollar.presentation.state

import edu.ucb.project.dollar.domain.model.DollarModel

data class DollarState(
    val officialInput: String = "",
    val parallelInput: String = "",
    val list: List<DollarModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)