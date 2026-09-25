package edu.ucb.project.movies.data.repository

import edu.ucb.project.movies.domain.model.MovieModel
import edu.ucb.project.movies.domain.repository.MovieRepository
import kotlinx.coroutines.delay

class MovieRepositoryImpl : MovieRepository {
    override suspend fun getMovies(): List<MovieModel> {
        delay(500)
        return listOf(
            MovieModel(id = "1", title = "Matrix", posterUrl = "poster_matrix.jpg"),
            MovieModel(id = "2", title = "Interstellar", posterUrl = "poster_interstellar.jpg"),
            MovieModel(id = "3", title = "Inception", posterUrl = "poster_inception.jpg")
        )
    }
}
