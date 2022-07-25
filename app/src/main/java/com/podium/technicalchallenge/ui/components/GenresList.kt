package com.podium.technicalchallenge.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.ui.theme.HalfPadding
import com.podium.technicalchallenge.ui.theme.Shapes

@Composable
fun GenresList(
    modifier: Modifier = Modifier,
    genres: List<String>,
    isLoading: Boolean,
    showViewAllButton: Boolean,
    showAllGenre: Boolean,
    onGenreClicked: (genre: String?) -> Unit,
    onViewAllClicked: (() -> Unit)? = null
) {
    FlowRow(
        modifier = modifier.testTag("genres_list"),
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 0.dp,
        mainAxisSpacing = 8.dp
    ) {
        if (isLoading) {
            repeat(6) {
                GenreButton(
                    modifier = Modifier
                        .padding(bottom = HalfPadding)
                        .placeholder(visible = true, highlight = PlaceholderHighlight.fade()),
                    genre = "Genre loading",
                    onClick = {  }
                )
            }
        } else {
            if (showAllGenre) {
                GenreButton(
                    genre = stringResource(id = R.string.genre_all),
                    onClick = { onGenreClicked(null) }
                )
            }

            genres.forEach { genre ->
                GenreButton(
                    genre = genre,
                    onClick = { onGenreClicked(genre) }
                )
            }
        }

        if (showViewAllButton) {
            TextButton(
                shape = Shapes.medium,
                onClick = { onViewAllClicked?.invoke() }
            ) {
                Text(text = stringResource(id = R.string.home_view_all))
            }
        }
    }
}