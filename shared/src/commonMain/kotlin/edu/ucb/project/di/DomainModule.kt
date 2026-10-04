package edu.ucb.project.di

import edu.ucb.project.movies.domain.usecase.GetMoviesUseCase
import edu.ucb.project.signin.domain.usecase.LoginUseCase
import edu.ucb.project.signup.domain.usecase.RegisterUseCase
import edu.ucb.project.moviedetail.domain.usecase.GetMovieDetailUseCase
import edu.ucb.project.profile.domain.usecase.GetProfileUseCase
import edu.ucb.project.userinformation.domain.usecase.FindAliasUseCase
import edu.ucb.project.catalog.domain.usecase.GetCatalogUseCase
import edu.ucb.project.weather.domain.usecase.GetWeatherUseCase
import edu.ucb.project.dollar.domain.usecase.GetDollarListUseCase
import edu.ucb.project.dollar.domain.usecase.InsertDollarUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetCatalogUseCase)
    singleOf(::GetMoviesUseCase)
    singleOf(::LoginUseCase)
    singleOf(::RegisterUseCase)
    singleOf(::GetMovieDetailUseCase)
    singleOf(::GetProfileUseCase)
    singleOf(::FindAliasUseCase)
    singleOf(::GetWeatherUseCase)
    singleOf(::GetDollarListUseCase)
    singleOf(::InsertDollarUseCase)
}
//el caso de uso, single porque koin lee constructor y resuleve lo que necesita en repo