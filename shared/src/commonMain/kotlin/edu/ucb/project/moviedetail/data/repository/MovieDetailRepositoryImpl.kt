package edu.ucb.project.moviedetail.data.repository

import edu.ucb.project.moviedetail.domain.model.MovieDetailModel
import edu.ucb.project.moviedetail.domain.repository.MovieDetailRepository
import kotlinx.coroutines.delay

class MovieDetailRepositoryImpl : MovieDetailRepository {
    override suspend fun getMovieDetail(id: String): MovieDetailModel {
        delay(500)
        return MovieDetailModel(
            id = id,
            title = "Película $id",
            description = "Descripción de la película $id",
            posterUrl = "poster_$id.jpg"
        )
    }
}