package com.podium.technicalchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.podium.technicalchallenge.home.HomeScreen
import com.podium.technicalchallenge.movie.MovieScreen
import com.podium.technicalchallenge.movies.MoviesScreen

@Composable
fun MyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(navController)
        }

        composable(route = Movies.route) {
            MoviesScreen(navController)
        }

        composable(
            route = Movie.routeWithArgs,
            arguments = Movie.arguments
        ) { backStackEntry ->
            MovieScreen(
                navController,
                backStackEntry.arguments?.getInt(Movie.movieIdArg) ?: -1
            )
        }
    }
}

fun NavHostController.navigateToHome() {
    this.navigateSingleTopTo(Home.route)
}

fun NavHostController.navigateToMovie(movieId: Int) {
    this.navigate("${Movie.route}/$movieId")
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            inclusive = false
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // re-selecting the same item
        launchSingleTop = true
    }