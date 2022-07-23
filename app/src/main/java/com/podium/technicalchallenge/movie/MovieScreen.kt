package com.podium.technicalchallenge.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun MovieScreen(id: Int) {
    Column {
        Text(text = "MovieScreen")
        Text(text = "id: $id")
    }
}