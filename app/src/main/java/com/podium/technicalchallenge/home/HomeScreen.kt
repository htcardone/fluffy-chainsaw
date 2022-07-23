package com.podium.technicalchallenge.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.podium.technicalchallenge.navigation.Movie

@Composable
fun HomeScreen(navController: NavHostController) {
    Column {
        Text(text = "HomeScreen")
        Button(onClick = {
            navController.navigate(Movie.route)
        }) {
            Text(text = "movies")
        }
    }
}