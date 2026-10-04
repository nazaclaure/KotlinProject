package edu.ucb.project.dollar.presentation.state

sealed interface DollarEvents {
    data class OnOfficialChange(val value: String) : DollarEvents
    data class OnParallelChange(val value: String) : DollarEvents
    object OnAddRecord : DollarEvents
}