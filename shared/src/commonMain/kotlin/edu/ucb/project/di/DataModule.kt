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
import edu.ucb.project.catalog.data.datasource.CatalogRemoteDataSource
import edu.ucb.project.catalog.data.repository.CatalogRepositoryImpl
import edu.ucb.project.catalog.data.service.CatalogService
import edu.ucb.project.catalog.domain.repository.CatalogRepository
import edu.ucb.project.weather.data.datasource.WeatherRemoteDataSource
import edu.ucb.project.weather.data.repository.WeatherRepositoryImpl
import edu.ucb.project.weather.data.service.WeatherService
import edu.ucb.project.weather.domain.repository.WeatherRepository
import edu.ucb.project.config.AppDatabase
import edu.ucb.project.dollar.data.dao.DollarDao
import edu.ucb.project.dollar.data.datasource.DollarLocalDataSource
import edu.ucb.project.dollar.data.datasource.RealTimeDataBase // NUEVO
import edu.ucb.project.dollar.data.repository.DollarRepositoryImpl
import edu.ucb.project.dollar.data.repository.ExchangeRepositoryImpl // NUEVO
import edu.ucb.project.dollar.domain.repository.DollarRepository
import edu.ucb.project.dollar.domain.repository.ExchangeRepository // NUEVO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single<CatalogRemoteDataSource> { CatalogService() }
    single<CatalogRepository> { CatalogRepositoryImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl() }
    single<AuthRepository> { AuthRepositoryImpl() }
    single<SignUpRepository> { SignUpRepositoryImpl() }
    single<MovieDetailRepository> { MovieDetailRepositoryImpl() }
    single<ProfileRepository> { ProfileRepositoryImpl() }
    single<GithubRemoteDataSource> { GitHubApiService() }
    single<GithubRepository> { GithubRepositoryImpl(get()) }
    single<WeatherRemoteDataSource> { WeatherService() }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    single<DollarDao> { get<AppDatabase>().getDao() }
    singleOf(::DollarLocalDataSource)
    single<DollarRepository> { DollarRepositoryImpl(get()) }
    singleOf(::RealTimeDataBase) // NUEVO
    single<ExchangeRepository> { ExchangeRepositoryImpl(get()) } // NUEVO
}

//implementaciones en la capa de datos, single porque lo hacemos manual interfaz aountada a su impl
//get le pide la dependencia