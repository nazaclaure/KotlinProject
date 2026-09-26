package edu.ucb.project.catalog.domain.usecase

import edu.ucb.project.catalog.domain.model.CatalogMovieModel
import edu.ucb.project.catalog.domain.repository.CatalogRepository

class GetCatalogUseCase(private val repository: CatalogRepository) {
    suspend operator fun invoke(): Result<List<CatalogMovieModel>> = repository.getPopularMovies()
}