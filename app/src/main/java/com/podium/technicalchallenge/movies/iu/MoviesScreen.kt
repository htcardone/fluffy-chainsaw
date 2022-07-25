@file:OptIn(ExperimentalMaterialApi::class)

package com.podium.technicalchallenge.movies.iu

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.data.network.queries.OrderBy
import com.podium.technicalchallenge.data.network.queries.Sort
import com.podium.technicalchallenge.movies.MoviesViewEvent
import com.podium.technicalchallenge.movies.MoviesViewState
import com.podium.technicalchallenge.ui.components.ErrorSnackBar
import com.podium.technicalchallenge.ui.components.GenresList
import com.podium.technicalchallenge.ui.components.MovieItem
import com.podium.technicalchallenge.ui.components.MovieItemPlaceHolder
import com.podium.technicalchallenge.ui.theme.*
import kotlinx.coroutines.launch

enum class BottomSheetContentType {
    GENRE,
    SORT
}

@Composable
fun MoviesScreen(
    viewState: MoviesViewState,
    sendEvent: (MoviesViewEvent) -> Unit
) {

    var bottomSheetContent by remember { mutableStateOf(BottomSheetContentType.GENRE) }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = shape,
        sheetBackgroundColor = DarkerBlue,
        scrimColor = Black70,
        sheetContent = {
            Column(
                modifier = Modifier.padding(DefaultPadding)
            ) {
                when (bottomSheetContent) {
                    BottomSheetContentType.GENRE ->
                        BottomSheetGenreContent(
                            genres = viewState.genres,
                            isLoading = viewState.isLoadingGenres,
                            onGenreClicked = { genre ->
                                sendEvent(MoviesViewEvent.OnGenreClicked(genre))
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                            }
                        )

                    BottomSheetContentType.SORT ->
                        BottomSheetSortContent(
                            onSortClicked = { orderBy, sort ->
                                sendEvent(MoviesViewEvent.OnSortClicked(orderBy, sort))
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                            }
                        )
                }
            }
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
        ) {
            MoviesContent(
                viewState = viewState,
                onGenreClicked = {
                    bottomSheetContent = BottomSheetContentType.GENRE
                    scope.launch { bottomSheetState.show() }
                 },
                onSortClicked = {
                    bottomSheetContent = BottomSheetContentType.SORT
                    scope.launch { bottomSheetState.show() }
                },
                sendEvent = sendEvent
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
private fun BottomSheetGenreContent(
    genres: List<String>,
    isLoading: Boolean,
    onGenreClicked: (genre: String?) -> Unit
) {
    Text(
        modifier = Modifier.padding(
            top = 0.dp, bottom = HalfPadding,
            start = 0.dp, end = 0.dp
        ),
        text = stringResource(id = R.string.movies_browse_by),
    )

    GenresList(
        genres = genres,
        isLoading = isLoading,
        showViewAllButton = false,
        showAllGenre = true,
        onGenreClicked = onGenreClicked
    )
}

@Composable
private fun SortButton(
    icon: ImageVector,
    @StringRes contentDescription: Int,
    @StringRes text: Int,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = contentDescription)
        )
        Text(text = stringResource(id = text))
    }
}

@Composable
private fun BottomSheetSortContent(
    onSortClicked: (String, String) -> Unit
) {
    Text(
        modifier = Modifier.padding(top = 0.dp, bottom = HalfPadding, start = 0.dp, end = 0.dp),
        text = stringResource(id = R.string.movies_sort_by),
    )

    Row {
        Column {
            SortButton(
                Icons.Filled.KeyboardArrowDown,
                R.string.a11y_sort_desc_icon,
                R.string.movies_sort_by_title,
                onClick = { onSortClicked(OrderBy.TITLE, Sort.DESC) }
            )

            Spacer(modifier = Modifier.height(HalfPadding))

            SortButton(
                Icons.Filled.KeyboardArrowDown,
                R.string.a11y_sort_desc_icon,
                R.string.movies_sort_by_rating,
                onClick = { onSortClicked(OrderBy.VOTE_AVERAGE, Sort.DESC) }
            )

            Spacer(modifier = Modifier.height(HalfPadding))

            SortButton(
                Icons.Filled.KeyboardArrowDown,
                R.string.a11y_sort_desc_icon,
                R.string.movies_sort_by_popularity,
                onClick = { onSortClicked(OrderBy.POPULARITY, Sort.DESC) }
            )
        }

        Spacer(modifier = Modifier.width(HalfPadding))

        Column {
            SortButton(
                Icons.Filled.KeyboardArrowUp,
                R.string.a11y_sort_asc_icon,
                R.string.movies_sort_by_title,
                onClick = { onSortClicked(OrderBy.TITLE, Sort.ASC) }
            )

            Spacer(modifier = Modifier.height(HalfPadding))

            SortButton(
                Icons.Filled.KeyboardArrowUp,
                R.string.a11y_sort_asc_icon,
                R.string.movies_sort_by_rating,
                onClick = { onSortClicked(OrderBy.VOTE_AVERAGE, Sort.ASC) }
            )

            Spacer(modifier = Modifier.height(HalfPadding))

            SortButton(
                Icons.Filled.KeyboardArrowUp,
                R.string.a11y_sort_asc_icon,
                R.string.movies_sort_by_popularity,
                onClick = { onSortClicked(OrderBy.POPULARITY, Sort.ASC) }
            )
        }
    }
}

@Composable
private fun MoviesContent(
    viewState: MoviesViewState,
    onGenreClicked: () -> Unit,
    onSortClicked: () -> Unit,
    sendEvent: (MoviesViewEvent) -> Unit
) {
    Column {
        val icon = if (viewState.sort == Sort.DESC) Icons.Filled.KeyboardArrowDown
        else Icons.Filled.KeyboardArrowUp

        val orderByText = when (viewState.orderBy) {
            OrderBy.POPULARITY -> R.string.movies_sort_by_popularity
            OrderBy.TITLE -> R.string.movies_sort_by_title
            OrderBy.VOTE_AVERAGE -> R.string.movies_sort_by_rating
            else -> R.string.movies_sort_by_rating
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onGenreClicked
            ) {
                val text = viewState.genre?.let {
                    stringResource(id = R.string.movies_genre_movies, viewState.genre)
                } ?: stringResource(id = R.string.movies_all_movies)

                Text(text = text)
            }

            Text(
                text = stringResource(id = R.string.movies_ordered_by),
                style = Typography.button
            )

            TextButton(
                onClick = onSortClicked
            ) {
                Text(text = stringResource(id = orderByText))
                Icon(icon, null)
            }
        }

        MoviesGrid(viewState, sendEvent)
    }
}

@Composable
private fun MoviesGrid(
    viewState: MoviesViewState,
    sendEvent: (MoviesViewEvent) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().testTag("movies_grid"),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(DefaultPadding),
        horizontalArrangement = Arrangement.spacedBy(HalfPadding),
        contentPadding = PaddingValues(HalfPadding)
    ) {
        if (viewState.isLoadingMovies) {
            items(6) {
                MovieItemPlaceHolder()
            }
        } else {
            items(viewState.movies) { movie ->
                MovieItem(
                    movie = movie,
                    onMovieClick = { movieId ->
                        sendEvent(MoviesViewEvent.OnMovieClicked(movieId))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewBottomSheetGenreContent() {
    MyTheme {
        Surface {
            Column(
                modifier = Modifier
                    .padding(DefaultPadding)
                    .fillMaxWidth()
            ) {
                BottomSheetGenreContent(
                    genres = listOf("Action", "Comedy", "Crime"),
                    isLoading = false
                ) {}
            }
        }
    }
}

@Preview
@Composable
fun PreviewBottomSheetSortContent() {
    MyTheme {
        Surface {
            Column(
                modifier = Modifier
                    .padding(DefaultPadding)
                    .fillMaxWidth()
            ) {
                BottomSheetSortContent() {_, _ ->}
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMoviesScreen() {
    val movie = MovieEntity(
        508442,
        "Mortal Kombat Legends: Scorpion's Revenge",
        "2020-12-25",
        8.3f,
        null
    )

    val viewState = MoviesViewState(
        movies = listOf(movie, movie, movie, movie, movie),
        isLoadingMovies = false
    )

    MyTheme {
        MoviesScreen(viewState = viewState, sendEvent = {})
    }
}