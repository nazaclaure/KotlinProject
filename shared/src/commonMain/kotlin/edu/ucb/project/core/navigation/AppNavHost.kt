package edu.ucb.project.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.ucb.project.signin.presentation.screen.LoginScreen
import edu.ucb.project.signup.presentation.screen.SignUpScreen
import edu.ucb.project.movies.presentation.screen.MovieScreen
import edu.ucb.project.moviedetail.presentation.screen.MovieDetailScreen
import edu.ucb.project.profile.presentation.screen.ProfileScreen
import edu.ucb.project.userinformation.presentation.screen.UserInformationScreen
import edu.ucb.project.catalog.presentation.screen.CatalogScreen
import edu.ucb.project.weather.presentation.screen.WeatherScreen
import edu.ucb.project.dollar.presentation.screen.DollarScreen
import edu.ucb.project.dollar.presentation.screen.ExchangeScreen
import edu.ucb.project.remoteconfig.presentation.screen.ConfigScreen
import edu.ucb.project.animations.presentation.screen.AnimationsScreen

// Duracion de todas las transiciones (en milisegundos)
private const val DURACION_TRANSICION = 400

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.Login,

        // Al ir a una pantalla nueva: entra desde la derecha con fundido
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(DURACION_TRANSICION)
            ) + fadeIn(animationSpec = tween(DURACION_TRANSICION))
        },

        // La pantalla que se queda atras se va hacia la izquierda
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(DURACION_TRANSICION)
            ) + fadeOut(animationSpec = tween(DURACION_TRANSICION))
        },

        // Al volver atras: la pantalla anterior entra desde la izquierda
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(DURACION_TRANSICION)
            ) + fadeIn(animationSpec = tween(DURACION_TRANSICION))
        },

        // Al volver atras: la pantalla actual sale hacia la derecha
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(DURACION_TRANSICION)
            ) + fadeOut(animationSpec = tween(DURACION_TRANSICION))
        }
    ) {

        composable<NavRoute.Login> {
            LoginScreen(navController = navController)
        }

        composable<NavRoute.SignUp> {
            SignUpScreen(navController = navController)
        }

        composable<NavRoute.Movies> {
            MovieScreen(navController = navController)
        }

        composable<NavRoute.MovieDetail> { backStackEntry ->
            val route: NavRoute.MovieDetail = backStackEntry.toRoute()

            MovieDetailScreen(
                movieId = route.movieId,
                navController = navController
            )
        }

        composable<NavRoute.Profile> {
            ProfileScreen(navController = navController)
        }

        composable<NavRoute.UserInformation> {
            UserInformationScreen(navController = navController)
        }

        composable<NavRoute.Catalog> {
            CatalogScreen(navController = navController)
        }

        composable<NavRoute.Weather> {
            WeatherScreen(navController = navController)
        }

        composable<NavRoute.Dollar> {
            DollarScreen(navController = navController)
        }

        composable<NavRoute.Exchange> {
            ExchangeScreen(navController = navController)
        }

        composable<NavRoute.Config> {
            ConfigScreen()
        }

        composable<NavRoute.Animations> {
            AnimationsScreen(navController = navController)
        }
    }
}

// conecta cada ruta con su pantalla