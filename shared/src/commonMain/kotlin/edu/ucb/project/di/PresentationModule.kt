package edu.ucb.project.di

import edu.ucb.project.movies.presentation.state.MovieVM
import edu.ucb.project.signin.presentation.state.SignInVM
import edu.ucb.project.signup.presentation.state.SignUpVM
import edu.ucb.project.moviedetail.presentation.state.MovieDetailVM
import edu.ucb.project.profile.presentation.state.ProfileVM
import edu.ucb.project.userinformation.presentation.viewmodel.UserInformationViewModel
import edu.ucb.project.catalog.presentation.state.CatalogVM
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::CatalogVM)
    viewModelOf(::MovieVM)
    viewModelOf(::SignInVM)
    viewModelOf(::SignUpVM)
    viewModelOf(::MovieDetailVM)
    viewModelOf(::ProfileVM)
    viewModelOf(::UserInformationViewModel)
}
