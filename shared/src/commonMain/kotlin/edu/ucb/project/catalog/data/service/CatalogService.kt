package edu.ucb.project.catalog.data.service

import edu.ucb.project.catalog.data.datasource.CatalogRemoteDataSource
import edu.ucb.project.catalog.data.dto.CatalogDto
import edu.ucb.project.catalog.data.mapper.toModel
import edu.ucb.project.catalog.domain.model.CatalogMovieModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CatalogService : CatalogRemoteDataSource {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override suspend fun fetchData(): Result<List<CatalogMovieModel>> {
        return try {
            val url = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=fa3e844ce31744388e07fa47c7c5d8c3"
            val response = client.get(url).body<CatalogDto>()
            Result.success(response.results.map { it.toModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}