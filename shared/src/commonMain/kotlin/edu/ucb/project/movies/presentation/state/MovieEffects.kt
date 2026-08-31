package edu.ucb.project.movies.presentation.state

sealed interface MovieEffects {
    data class NavigateToDetail(val movieId: Int) : MovieEffects
    data class ShowToast(val message: String) : MovieEffects
}