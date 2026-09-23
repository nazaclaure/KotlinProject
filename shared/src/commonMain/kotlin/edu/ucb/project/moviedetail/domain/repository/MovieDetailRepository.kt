package edu.ucb.project.moviedetail.domain.repository

import edu.ucb.project.moviedetail.domain.model.MovieDetailModel

interface MovieDetailRepository {
    suspend fun getMovieDetail(id: String): MovieDetailModel
}
