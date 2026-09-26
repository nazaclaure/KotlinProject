package edu.ucb.project.catalog.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatalogDto(
    val page: Int,
    val results: List<MovieDto>
)

@Serializable
data class MovieDto(
    val title: String,
    @SerialName("poster_path")
    val posterPath: String
)