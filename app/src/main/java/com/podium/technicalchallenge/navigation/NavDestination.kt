package com.podium.technicalchallenge.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface MyNavDestination {
    val route: String
}

object Home: MyNavDestination {
    override val route = "home"
}

object Movies: MyNavDestination {
    override val route = "movies"
}

object Movie: MyNavDestination {
    override val route = "movies"
    const val movieIdArg = "movieId"

    val routeWithArgs = "$route/{$movieIdArg}"

    val arguments = listOf(
        navArgument(movieIdArg) {
            type = NavType.IntType
        }
    )
}