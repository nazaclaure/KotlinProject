package edu.ucb.project.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute {
    @Serializable
    object Login : NavRoute()

    @Serializable
    object SignUp : NavRoute()

    @Serializable
    object Movies : NavRoute()

    @Serializable
    data class MovieDetail(val movieId: String) : NavRoute()

    @Serializable
    object Profile : NavRoute()

    @Serializable
    object UserInformation : NavRoute()

    @Serializable
    object Catalog : NavRoute()
}
