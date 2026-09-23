package edu.ucb.project.moviedetail.domain.usecase

import edu.ucb.project.moviedetail.domain.model.MovieDetailModel
import edu.ucb.project.moviedetail.domain.repository.MovieDetailRepository

class GetMovieDetailUseCase(private val repository: MovieDetailRepository) {
    suspend operator fun invoke(id: String): MovieDetailModel = repository.getMovieDetail(id)
}
