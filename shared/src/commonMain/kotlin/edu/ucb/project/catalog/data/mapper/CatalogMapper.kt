package edu.ucb.project.catalog.data.mapper

import edu.ucb.project.catalog.data.dto.MovieDto
import edu.ucb.project.catalog.domain.model.CatalogMovieModel

fun MovieDto.toModel(): CatalogMovieModel {
    return CatalogMovieModel(title = title, posterPath = posterPath)
}