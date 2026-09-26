package edu.ucb.project.catalog.data.repository

import edu.ucb.project.catalog.data.datasource.CatalogRemoteDataSource
import edu.ucb.project.catalog.domain.model.CatalogMovieModel
import edu.ucb.project.catalog.domain.repository.CatalogRepository

class CatalogRepositoryImpl(
    private val dataSource: CatalogRemoteDataSource
) : CatalogRepository {
    override suspend fun getPopularMovies(): Result<List<CatalogMovieModel>> {
        return dataSource.fetchData()
    }
}