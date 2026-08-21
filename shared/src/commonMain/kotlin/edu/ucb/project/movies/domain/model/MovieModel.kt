package edu.ucb.project.movies.domain.model

import edu.ucb.project.movies.domain.vo.PosterPath

data class MovieModel(
    val title: String,
    val description: String,
    val posterPath: PosterPath
)