package com.podium.technicalchallenge.ui.components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.podium.technicalchallenge.ui.theme.Blue
import com.podium.technicalchallenge.ui.theme.MyTheme
import com.podium.technicalchallenge.ui.theme.Shapes

@Composable
fun GenreButton(modifier: Modifier = Modifier, genre: String, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        shape = Shapes.medium,
        colors = ButtonDefaults.buttonColors(backgroundColor = Blue),
        onClick = onClick
    ) {
        Text(
            text = genre,
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview
@Composable
fun PreviewGenreButton() {
    MyTheme {
        GenreButton(genre = "Action") {

        }
    }
}