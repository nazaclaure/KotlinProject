package edu.ucb.project.moviedetail.presentation.state

import edu.ucb.project.moviedetail.domain.model.MovieDetailModel

data class MovieDetailState(
    val detail: MovieDetailModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
