package com.podium.technicalchallenge.movie.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.data.entity.CastEntity
import com.podium.technicalchallenge.data.entity.DirectorEntity
import com.podium.technicalchallenge.data.entity.MovieFullEntity
import com.podium.technicalchallenge.movie.MovieViewEvent
import com.podium.technicalchallenge.movie.MovieViewState
import com.podium.technicalchallenge.ui.components.GenreButton
import com.podium.technicalchallenge.ui.theme.*
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieScreen(
    viewState: MovieViewState,
    sendEvent: (MovieViewEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        if (viewState.isLoading) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DefaultPadding),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                // TODO improve loading UI
            }
        } else {
            viewState.movie?.let { movie ->
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    MovieContent(movie, sendEvent)
                }
            }

            viewState.errorMessage?.let { error ->
                // TODO change message based on error
                val message = stringResource(id = R.string.error_loading)
                val actionLabel = stringResource(id = R.string.try_again)
                showErrorSnackBar(
                    message,
                    actionLabel,
                    scaffoldState,
                    onDismiss = {
                        sendEvent(MovieViewEvent.OnErrorShown(error, false))
                    },
                    onConfirm = {
                        sendEvent(MovieViewEvent.OnErrorShown(error, true))
                    }
                )
            }
        }
    }
}

@Composable
private fun MovieContent(movie: MovieFullEntity, sendEvent: (MovieViewEvent) -> Unit) {
    Poster(movie.posterPath)

    RatingRow(movie.voteAverage, movie.voteCount)

    Text(
        modifier = Modifier.padding(
            vertical = 0.dp,
            horizontal = DefaultPadding
        ),
        text = movie.title,
        color = Color.White,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    )

    Text(
        modifier = Modifier.padding(
            vertical = 2.dp,
            horizontal = DefaultPadding
        ),
        text = stringResource(id = R.string.movies_runtime_year, movie.runtime,
            movie.releaseDate.substringBefore("-")),
        color = Color.White,
        style = Typography.caption
    )

    Text(
        modifier = Modifier.padding(
            vertical = DefaultPadding,
            horizontal = DefaultPadding
        ),
        text = movie.overview,
        color = Color.White,
        style = Typography.caption
    )

    Text(
        modifier = Modifier.padding(
            top = DefaultPadding, bottom = HalfPadding,
            start = DefaultPadding, end = DefaultPadding
        ),
        text = stringResource(id = R.string.movie_genres),
        color = Color.White,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    )

    GenresRow(
        genres = movie.genres,
        onGenreClicked = { genre ->
            sendEvent(MovieViewEvent.OnGenreClicked(genre))
        }
    )

    Text(
        modifier = Modifier.padding(
            top = DefaultPadding, bottom = HalfPadding,
            start = DefaultPadding, end = DefaultPadding
        ),
        text = stringResource(id = R.string.movie_cast),
        color = Color.White,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    )

    CastRow(movie.cast)

    Text(
        modifier = Modifier.padding(
            top = DefaultPadding, bottom = HalfPadding,
            start = DefaultPadding, end = DefaultPadding
        ),
        text = stringResource(id = R.string.movie_directed_by),
        color = Color.White,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    )

    PersonItem(
        modifier = Modifier
            .padding(horizontal = HalfPadding, vertical = 0.dp)
            .testTag("director_item"),
        image = null,
        name = movie.director.name
    )

    Spacer(modifier = Modifier.height(DefaultPadding))
}

@Composable
private fun GenresRow(genres: List<String>, onGenreClicked: (String) -> Unit) {
    FlowRow(
        modifier = Modifier
            .padding(horizontal = DefaultPadding, vertical = 0.dp)
            .testTag("genres_row"),
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 0.dp,
        mainAxisSpacing = 8.dp
    ) {
        genres.forEach { genre ->
            GenreButton(
                genre = genre,
                onClick = { onGenreClicked(genre) }
            )
        }
    }
}

@Composable
private fun CastRow(cast: List<CastEntity>) {
    LazyRow(
        modifier = Modifier.testTag("cast_row"),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(HalfPadding)
    ) {
        items(cast) { item ->
            val image = item.profilePath
            val name = item.name

            PersonItem(image = image, name = name)
        }
    }
}

@Composable
private fun PersonItem(modifier: Modifier = Modifier, image: Any?, name: String) {
    Column(
        modifier = modifier.width(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            imageModel = image,
            contentScale = ContentScale.Crop,
            previewPlaceholder = R.drawable.ic_person,
            placeHolder = ImageVector.vectorResource(id = R.drawable.ic_person),
            error = ImageVector.vectorResource(R.drawable.ic_person)
        )

        Text(
            modifier = Modifier.padding(
                vertical = 2.dp,
                horizontal = 2.dp
            ),
            text = name,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.White,
            fontSize = 10.sp,
            style = Typography.caption
        )
    }
}

@Composable
private fun RatingRow(rating: Float, voteCount: Int) {
    Row(
        modifier = Modifier.padding(
            start = DefaultPadding,
            end = DefaultPadding,
            top = 0.dp,
            bottom = HalfPadding
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = stringResource(id = R.string.a11y_rating_icon)
        )
        Text(
            text = " $rating",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Text(
            text = " | $voteCount",
            color = Color.White,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun Poster(posterPath: String?) {
    val transparentColor = Color(0xFFFFFF)
    val gradient = Brush.verticalGradient(
        listOf(transparentColor, DarkerBlue),
        startY = 0.0f,
        endY = 100.0f
    )

    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            imageModel = posterPath,
            contentScale = ContentScale.Crop,
            circularReveal = CircularReveal(duration = 350),
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

        Box(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(gradient)
        )
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

@Preview
@Composable
fun PreviewCastRow() {
    val cast = listOf(
        CastEntity("Person First Large Name", null),
        CastEntity("Person Name", null),
        CastEntity("Person Name", null),
        CastEntity("Person Name", null),
        CastEntity("Person Name", null)
    )

    MyTheme {
        CastRow(cast = cast)
    }
}

@Preview
@Composable
private fun PreviewMovieScreen() {
    val movie = MovieFullEntity(
        508442,
        "Mortal Kombat Legends: Scorpion's Revenge",
        "2020-04-12",
        8.4f,
        731,
        80,
        "After the vicious slaughter of his family by stone-cold mercenary Sub-Zero, Hanzo Hasashi is exiled to the torturous Netherrealm. There, in exchange for his servitude to the sinister Quan Chi, he’s given a chance to avenge his family – and is resurrected as Scorpion, a lost soul bent on revenge. Back on Earthrealm, Lord Raiden gathers a team of elite warriors – Shaolin monk Liu Kang, Special Forces officer Sonya Blade and action star Johnny Cage – an unlikely band of heroes with one chance to save humanity. To do this, they must defeat Shang Tsung’s horde of Outworld gladiators and reign over the Mortal Kombat tournament.",
        null,
        listOf("Fantasy", "Action", "Adventure", "Animation"),
        DirectorEntity(0, "Director Name"),
        listOf(
            CastEntity("Person Name", null),
            CastEntity("Person Name", null)
        )
    )

    val viewState = MovieViewState(
        movie = movie
    )

    MyTheme {
        MovieScreen(viewState = viewState) {}
    }
}