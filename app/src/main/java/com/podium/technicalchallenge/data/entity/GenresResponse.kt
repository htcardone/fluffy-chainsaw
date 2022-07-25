package com.podium.technicalchallenge.data.entity

import androidx.annotation.Keep

@Keep
data class GenresResponse(
    val data: Genres
)

@Keep
data class Genres(
    val genres: List<String>
)