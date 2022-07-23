package com.podium.technicalchallenge.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.podium.technicalchallenge.navigation.navigateToHome
import com.podium.technicalchallenge.navigation.navigateToMovie

@Composable
fun MoviesScreen(navController: NavHostController) {
    Column {
        Text(text = "MoviesScreen")
        Button(onClick = { navController.navigateToHome() }) {
            Text(text = "home")
        }

        Button(onClick = { navController.navigateToMovie(0) }) {
            Text(text = "movie/0")
        }

        Button(onClick = { navController.navigateToMovie(1) }) {
            Text(text = "movie/1")
        }
    }
}