package com.podium.technicalchallenge.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.podium.technicalchallenge.navigation.navigateToHome

@Composable
fun MovieScreen(navController: NavHostController, id: Int) {
    Column {
        Text(text = "MovieScreen")
        Text(text = "id: $id")

        Button(
            onClick = {
                navController.navigateToHome()
            }
        ) {
            Text(text = "home")
        }
    }
}