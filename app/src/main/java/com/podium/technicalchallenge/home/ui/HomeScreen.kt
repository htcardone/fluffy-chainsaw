package com.podium.technicalchallenge.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.podium.technicalchallenge.ui.theme.DefaultPadding
import com.podium.technicalchallenge.ui.theme.HalfPadding
import com.podium.technicalchallenge.ui.theme.MyTheme

@Composable
fun HomeScreen(
    onMoviesClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(top = DefaultPadding, bottom = HalfPadding,
                start = DefaultPadding, end = DefaultPadding),
            text = "Top Movies",
            style = MaterialTheme.typography.h4
        )

        Button(onClick = { onMoviesClick() }) {
            Text(text = "movies")
        }
    }
}

@Preview(
    showBackground = false,
    backgroundColor = 0xFFFFFFFF,
    device = Devices.NEXUS_5,
    showSystemUi = true,
)
@Composable
fun PreviewHomeScree() {
    MyTheme {
        HomeScreen({}, {})
    }
}