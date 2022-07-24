package com.podium.technicalchallenge.home.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.home.HomeViewEvent
import com.podium.technicalchallenge.home.HomeViewState
import com.podium.technicalchallenge.ui.components.GenreButton
import com.podium.technicalchallenge.ui.theme.*
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeScreen(
    viewState: HomeViewState,
    sendEvent: (HomeViewEvent) -> Unit
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
                movies = viewState.topMovies,
                isLoading = viewState.isLoadingMovies,
                onMovieClicked = { movieId ->
                    sendEvent(HomeViewEvent.OnMovieClicked(movieId))
                }
            )

            Text(
                modifier = Modifier.padding(top = DefaultPadding, bottom = HalfPadding,
                    start = HalfPadding, end = DefaultPadding
                ),
                text = stringResource(id = R.string.home_browse_by_genre),
                style = MaterialTheme.typography.h6
            )

            GenresRow(
                genres = viewState.genres,
                isLoading = viewState.isLoadingGenres,
                onGenreClicked = { genre ->
                    sendEvent(HomeViewEvent.OnGenreClicked(genre))
                },
                onViewAllClicked = {
                    sendEvent(HomeViewEvent.OnViewAllClicked)
                }
            )

            viewState.errorMessage?.let { error ->
                // TODO change message based on error
                val message = stringResource(id = R.string.error_loading)
                val actionLabel = stringResource(id = R.string.try_again)
                showErrorSnackBar(
                    message,
                    actionLabel,
                    scaffoldState,
                    onDismiss = {
                        sendEvent(HomeViewEvent.OnErrorShown(error, false))
                    },
                    onConfirm = {
                        sendEvent(HomeViewEvent.OnErrorShown(error, true))
                    }
                )
            }
        }
    }
}

@Composable
private fun showErrorSnackBar(
    message: String,
    actionLabel: String,
    scaffoldState: ScaffoldState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    LaunchedEffect(message) {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Long
        )

        when (result) {
            SnackbarResult.Dismissed -> onDismiss()
            SnackbarResult.ActionPerformed -> onConfirm()
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
        horizontalArrangement = Arrangement.spacedBy(HalfPadding),
        contentPadding = PaddingValues(HalfPadding)
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

@Composable
private fun GenresRow(
    genres: List<String>,
    isLoading: Boolean,
    onGenreClicked: (genre: String) -> Unit,
    onViewAllClicked: () -> Unit
) {
    FlowRow(
        modifier = Modifier.padding(HalfPadding),
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
            genres.forEach { genre ->
                GenreButton(
                    genre = genre,
                    onClick = { onGenreClicked(genre) }
                )
            }
        }

        TextButton(
            shape = Shapes.medium,
            onClick = onViewAllClicked
        ) {
            Text(text = stringResource(id = R.string.home_view_all))
        }
    }
}

@Composable
private fun Poster(modifier: Modifier = Modifier, path: String?) {
    GlideImage(
        modifier = modifier
            .height(180.dp)
            .clip(Shapes.medium),
        imageModel = path,
        contentScale = ContentScale.Crop,
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colors.background,
            highlightColor = Blue,
            durationMillis = 350,
            dropOff = 0.65f,
            tilt = 20f
        ),
        previewPlaceholder = R.drawable.poster,
        failure = {
            Text(text = stringResource(id = R.string.error_loading_image))
        }
    )
}

@Composable
private fun Rating(rating: Float) {
    val shape = RoundedCornerShape(0.dp, 4.dp, 0.dp, 4.dp)

    Row(
        modifier = Modifier
            .clip(shape)
            .background(Black50)
            .padding(start = 2.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(10.dp),
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = stringResource(id = R.string.a11y_rating_icon)
        )
        Text(
            text = " $rating",
            color = Yellow,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        )
    }
}

@Composable
private fun MovieItem(movie: MovieEntity, onMovieClick: (Int) -> Unit) {
    Column(
        modifier = Modifier.width(120.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Poster(
                modifier = Modifier.clickable { onMovieClick(movie.id) },
                path = movie.posterPath
            )
            Rating(rating = movie.voteAverage)
        }
        
        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = Typography.caption,
            letterSpacing = 0.sp
        )
    }
}

@Composable
private fun MovieItemPlaceHolder() {
    Column(
        modifier = Modifier.width(120.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(180.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.fade()
                )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .width(80.dp)
                .height(14.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.fade()
                )
        )
    }
}

@Preview
@Composable
private fun PreviewMovieItem() {
    val movie = MovieEntity(
        508442,
        "Mortal Kombat Legends: Scorpion's Revenge",
        "2020-12-25",
        8.3f,
        null
    )

    MyTheme {
        MovieItem(movie) {}
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

    val viewState = HomeViewState(
        topMovies = listOf(movie, movie, movie, movie, movie),
        genres = listOf("Action", "Adventure", "Animation", "Comedy", "Crime")
    )

    MyTheme {
        HomeScreen(viewState = viewState) {}
    }
}
