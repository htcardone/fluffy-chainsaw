package com.podium.technicalchallenge.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun MoviesScreen(onMovieClicked: (Int) -> Unit) {
    Column {
        Button(onClick = { onMovieClicked(0) }) {
            Text(text = "movie/0")
        }

        Button(onClick = { onMovieClicked(1) }) {
            Text(text = "movie/1")
        }
    }
}