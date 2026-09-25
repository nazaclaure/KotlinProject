package edu.ucb.project.di

import edu.ucb.project.movies.data.repository.MovieRepositoryImpl
import edu.ucb.project.movies.domain.repository.MovieRepository
import edu.ucb.project.signin.data.repository.AuthRepositoryImpl
import edu.ucb.project.signin.domain.repository.AuthRepository
import edu.ucb.project.signup.data.repository.SignUpRepositoryImpl
import edu.ucb.project.signup.domain.repository.SignUpRepository
import edu.ucb.project.moviedetail.data.repository.MovieDetailRepositoryImpl
import edu.ucb.project.moviedetail.domain.repository.MovieDetailRepository
import edu.ucb.project.profile.data.repository.ProfileRepositoryImpl
import edu.ucb.project.profile.domain.repository.ProfileRepository
import edu.ucb.project.userinformation.data.datasource.GithubRemoteDataSource
import edu.ucb.project.userinformation.data.repository.GithubRepositoryImpl
import edu.ucb.project.userinformation.data.service.GitHubApiService
import edu.ucb.project.userinformation.domain.repository.GithubRepository
import org.koin.dsl.module

val dataModule = module {
    single<MovieRepository> { MovieRepositoryImpl() }
    single<AuthRepository> { AuthRepositoryImpl() }
    single<SignUpRepository> { SignUpRepositoryImpl() }
    single<MovieDetailRepository> { MovieDetailRepositoryImpl() }
    single<ProfileRepository> { ProfileRepositoryImpl() }
    single<GithubRemoteDataSource> { GitHubApiService() }
    single<GithubRepository> { GithubRepositoryImpl(get()) }
}
