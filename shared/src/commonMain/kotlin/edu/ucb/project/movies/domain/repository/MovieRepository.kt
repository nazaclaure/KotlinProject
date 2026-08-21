package edu.ucb.project.movies.domain.repository

import edu.ucb.project.movies.domain.model.MovieModel

interface MovieRepository {
    suspend fun getMovies(): List<MovieModel>
}