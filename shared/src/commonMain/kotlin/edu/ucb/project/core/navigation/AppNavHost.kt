package edu.ucb.project.core.navigation

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

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Login) {
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
            MovieDetailScreen(movieId = route.movieId, navController = navController)
        }
        composable<NavRoute.Profile> {
            ProfileScreen(navController = navController)
        }
        composable<NavRoute.UserInformation> {
            UserInformationScreen(navController = navController)
        }
    }
}
