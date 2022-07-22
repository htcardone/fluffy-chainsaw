package com.podium.technicalchallenge.data.entity

data class GenresResponse(
    val data: Genres
)

data class Genres(
    val genres: List<String>
)