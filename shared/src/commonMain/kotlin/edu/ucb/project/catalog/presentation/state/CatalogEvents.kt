package edu.ucb.project.catalog.presentation.state

sealed interface CatalogEvents {
    object LoadCatalog : CatalogEvents
}