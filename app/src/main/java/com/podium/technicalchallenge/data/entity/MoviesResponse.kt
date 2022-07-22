package com.podium.technicalchallenge.data.entity

data class MoviesResponse(
    val data: Movies
)

data class MovieResponse(
    val data: Movie?
)

data class Movies(
    val movies: List<MovieEntity>
)

data class Movie(
    val movie: MovieFullEntity
)

data class MovieEntity(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: Float,
    val posterPath: String?
)

data class MovieFullEntity(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: Float,
    val overview: String,
    val posterPath: String?,
    val director: DirectorEntity,
    val cast: List<CastEntity>
)

data class CastEntity(
    val name: String,
    val profilePath: String?
)

data class DirectorEntity(
    val id: Int,
    val name: String
)

class MovieNotFoundException: Exception()