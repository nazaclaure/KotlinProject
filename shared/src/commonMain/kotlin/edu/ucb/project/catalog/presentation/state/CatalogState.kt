package edu.ucb.project.catalog.presentation.state

import edu.ucb.project.catalog.domain.model.CatalogMovieModel

data class CatalogState(
    val isLoading: Boolean = false,
    val movies: List<CatalogMovieModel> = emptyList(),
    val error: String? = null
)