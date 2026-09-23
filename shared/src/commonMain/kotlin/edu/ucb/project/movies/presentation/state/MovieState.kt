package edu.ucb.project.movies.presentation.state

import edu.ucb.project.movies.domain.model.MovieModel

data class MovieState(
    val movies: List<MovieModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
