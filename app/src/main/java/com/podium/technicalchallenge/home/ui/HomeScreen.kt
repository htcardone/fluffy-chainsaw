package com.podium.technicalchallenge.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.movies.MoviesViewEvent
import com.podium.technicalchallenge.movies.MoviesViewState
import com.podium.technicalchallenge.ui.components.ErrorSnackBar
import com.podium.technicalchallenge.ui.components.GenresList
import com.podium.technicalchallenge.ui.components.MovieItem
import com.podium.technicalchallenge.ui.components.MovieItemPlaceHolder
import com.podium.technicalchallenge.ui.theme.DefaultPadding
import com.podium.technicalchallenge.ui.theme.HalfPadding
import com.podium.technicalchallenge.ui.theme.MyTheme

@Composable
fun HomeScreen(
    viewState: MoviesViewState,
    sendEvent: (MoviesViewEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(top = DefaultPadding, bottom = HalfPadding,
                    start = HalfPadding, end = DefaultPadding
                ),
                text = stringResource(id = R.string.home_top_movies),
                style = MaterialTheme.typography.h6
            )

            TopMoviesRow(
                movies = viewState.movies,
                isLoading = viewState.isLoadingMovies,
                onMovieClicked = { movieId ->
                    sendEvent(MoviesViewEvent.OnMovieClicked(movieId))
                }
            )

            Text(
                modifier = Modifier.padding(top = DefaultPadding, bottom = HalfPadding,
                    start = HalfPadding, end = DefaultPadding
                ),
                text = stringResource(id = R.string.home_browse_by_genre),
                style = MaterialTheme.typography.h6
            )

            GenresList(
                modifier = Modifier.padding(HalfPadding),
                genres = viewState.genres,
                isLoading = viewState.isLoadingGenres,
                showViewAllButton = true,
                showAllGenre = false,
                onGenreClicked = { genre ->
                    genre?.let {
                        sendEvent(MoviesViewEvent.OnGenreClicked(it))
                    }
                },
                onViewAllClicked = {
                    sendEvent(MoviesViewEvent.OnViewAllClicked)
                }
            )

            viewState.errorMessage?.let { error ->
                ErrorSnackBar(
                    scaffoldState,
                    onDismiss = {
                        sendEvent(MoviesViewEvent.OnErrorShown(error, false))
                    },
                    onConfirm = {
                        sendEvent(MoviesViewEvent.OnErrorShown(error, true))
                    }
                )
            }
        }
    }
}

@Composable
private fun TopMoviesRow(
    movies: List<MovieEntity>,
    isLoading: Boolean,
    onMovieClicked: (movieId: Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.testTag("top_movies_row"),
        horizontalArrangement = Arrangement.spacedBy(HalfPadding),
        contentPadding = PaddingValues(HalfPadding),
    ) {
        if (isLoading) {
            items(5) {
                MovieItemPlaceHolder()
            }
        } else {
            items(movies) { movie ->
                MovieItem(movie, onMovieClicked)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    val movie = MovieEntity(
        508442,
        "Mortal Kombat Legends: Scorpion's Revenge",
        "2020-12-25",
        8.3f,
        null
    )

    val viewState = MoviesViewState(
        movies = listOf(movie, movie, movie, movie, movie),
        genres = listOf("Action", "Adventure", "Animation", "Comedy", "Crime")
    )

    MyTheme {
        HomeScreen(viewState = viewState) {}
    }
}
