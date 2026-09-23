package edu.ucb.project.moviedetail.presentation.state

sealed interface MovieDetailEffects {
    data object NavigateBack : MovieDetailEffects
}
