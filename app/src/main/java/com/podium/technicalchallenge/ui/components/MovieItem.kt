package com.podium.technicalchallenge.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.data.entity.MovieEntity
import com.podium.technicalchallenge.ui.theme.*
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieItem(movie: MovieEntity, onMovieClick: (Int) -> Unit) {
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
            durationMillis = 500,
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
fun MovieItemPlaceHolder() {
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