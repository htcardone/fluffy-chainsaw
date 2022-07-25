package com.podium.technicalchallenge.data.entity

import androidx.annotation.Keep

@Keep
data class MoviesResponse(
    val data: Movies
)

@Keep
data class MovieResponse(
    val data: Movie?
)

@Keep
data class Movies(
    val movies: List<MovieEntity>
)

@Keep
data class Movie(
    val movie: MovieFullEntity
)

@Keep
data class MovieEntity(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: Float,
    val posterPath: String?
)

@Keep
data class MovieFullEntity(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: Float,
    val voteCount: Int,
    val runtime: Int,
    val overview: String,
    val posterPath: String?,
    val genres: List<String>,
    val director: DirectorEntity,
    val cast: List<CastEntity>
)

@Keep
data class CastEntity(
    val name: String,
    val profilePath: String?
)

@Keep
data class DirectorEntity(
    val id: Int,
    val name: String
)

class MovieNotFoundException: Exception()