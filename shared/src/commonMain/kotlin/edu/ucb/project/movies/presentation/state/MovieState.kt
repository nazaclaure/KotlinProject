package edu.ucb.project.movies.presentation.state

import edu.ucb.project.movies.domain.model.MovieModel

data class MovieState(
    val isLoading: Boolean = false,
    val movies: List<MovieModel> = emptyList(),
    val error: String? = null
)