package edu.ucb.project.movies.domain.usecase

import edu.ucb.project.movies.domain.model.MovieModel
import edu.ucb.project.movies.domain.repository.MovieRepository

class GetPopularMovies(
    private val repository: MovieRepository
) {
    suspend fun invoke(): List<MovieModel> {
        return repository.getMovies()
    }
}