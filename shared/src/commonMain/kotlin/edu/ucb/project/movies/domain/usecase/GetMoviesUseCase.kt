package edu.ucb.project.movies.domain.usecase

import edu.ucb.project.movies.domain.model.MovieModel
import edu.ucb.project.movies.domain.repository.MovieRepository

class GetMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(): List<MovieModel> = repository.getMovies()
}
