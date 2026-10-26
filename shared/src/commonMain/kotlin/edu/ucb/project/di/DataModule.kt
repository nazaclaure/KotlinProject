package edu.ucb.project.di

import edu.ucb.project.dollar.data.datasource.DollarFirestoreDataSource

import edu.ucb.project.analytics.AnalyticsTracker
import edu.ucb.project.core.grpc.GrpcConfig
import edu.ucb.project.logs.data.datasource.LogsRemoteDataSource
import edu.ucb.project.logs.data.repository.LogsRepositoryImpl
import edu.ucb.project.logs.data.service.LogsService
import edu.ucb.project.logs.domain.repository.LogsRepository

import edu.ucb.project.remoteconfig.data.datasource.RemoteConfigDataSource
import edu.ucb.project.remoteconfig.data.repository.AppConfigRepositoryImpl
import edu.ucb.project.remoteconfig.domain.repository.AppConfigRepository

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
import edu.ucb.project.dollar.data.datasource.RealTimeDataBase
import edu.ucb.project.dollar.data.repository.DollarRepositoryImpl
import edu.ucb.project.dollar.data.repository.ExchangeRepositoryImpl
import edu.ucb.project.dollar.domain.repository.DollarRepository
import edu.ucb.project.dollar.domain.repository.ExchangeRepository
import edu.ucb.project.push.data.datasource.PushTokenDataBase // NUEVO
import edu.ucb.project.push.data.datasource.PushTokenFirestoreDataSource
import edu.ucb.project.push.data.provider.PushTokenProvider // NUEVO
import edu.ucb.project.push.data.repository.PushTokenRepositoryImpl // NUEVO
import edu.ucb.project.push.domain.repository.PushTokenRepository // NUEVO
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
    single<DollarRepository> { DollarRepositoryImpl(get(), get()) }
    singleOf(::RealTimeDataBase)
    single<ExchangeRepository> { ExchangeRepositoryImpl(get()) }
    singleOf(::PushTokenProvider)
    singleOf(::AnalyticsTracker)
    single<LogsRemoteDataSource> { LogsService(GrpcConfig.LOGS_URL) }
    single<LogsRepository> { LogsRepositoryImpl(get()) }
    singleOf(::DollarFirestoreDataSource) // NUEVO
    singleOf(::PushTokenDataBase)
    singleOf(::PushTokenFirestoreDataSource)
    singleOf(::RemoteConfigDataSource)
    single<AppConfigRepository> { AppConfigRepositoryImpl(get()) } // NUEVO
    single<PushTokenRepository> { PushTokenRepositoryImpl(get(), get(), get()) } // NUEVO
}

//implementaciones en la capa de datos, single porque lo hacemos manual interfaz aountada a su impl
//get le pide la dependencia