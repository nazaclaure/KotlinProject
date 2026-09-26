package edu.ucb.project.catalog.presentation.state

sealed interface CatalogEffects {
    data class ShowError(val message: String) : CatalogEffects
}