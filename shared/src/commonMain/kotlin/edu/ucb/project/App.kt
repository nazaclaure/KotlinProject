package edu.ucb.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import edu.ucb.project.movies.domain.model.MovieModel
import edu.ucb.project.movies.domain.repository.MovieRepository
import edu.ucb.project.movies.domain.usecase.GetPopularMovies
import edu.ucb.project.movies.domain.vo.PosterPath
import edu.ucb.project.movies.presentation.screen.MoviesScreen
import edu.ucb.project.movies.presentation.state.MovieVM

class MockMovieRepository : MovieRepository {
    override suspend fun getMovies(): List<MovieModel> {
        return listOf(
            MovieModel(
                title = "Matrix",
                description = "Descripcion 1",
                posterPath = PosterPath("poster_matrix.jpg")
            ),
            MovieModel(
                title = "Interstellar",
                description = "Descripcion 2",
                posterPath = PosterPath("poster_interstellar.jpg")
            ),
            MovieModel(
                title = "Inception",
                description = "Descripcion 3",
                posterPath = PosterPath("poster_inception.jpg")
            )
        )
    }
}

@Composable
fun App() {
    MaterialTheme {
        val repository = MockMovieRepository()
        val useCase = GetPopularMovies(repository)
        val viewModel = MovieVM(useCase)

        MoviesScreen(viewModel = viewModel)
    }
}