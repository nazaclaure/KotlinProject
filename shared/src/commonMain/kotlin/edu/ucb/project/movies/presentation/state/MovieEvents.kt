package edu.ucb.project.movies.presentation.state

sealed interface MovieEvents {
    data object LoadMovies : MovieEvents
    data class OnMovieClicked(val movieId: Int) : MovieEvents
}