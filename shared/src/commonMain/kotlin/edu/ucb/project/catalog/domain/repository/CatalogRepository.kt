package edu.ucb.project.catalog.domain.repository

import edu.ucb.project.catalog.domain.model.CatalogMovieModel

interface CatalogRepository {
    suspend fun getPopularMovies(): Result<List<CatalogMovieModel>>
}