package edu.ucb.project.catalog.data.datasource

import edu.ucb.project.catalog.domain.model.CatalogMovieModel

interface CatalogRemoteDataSource {
    suspend fun fetchData(): Result<List<CatalogMovieModel>>
}