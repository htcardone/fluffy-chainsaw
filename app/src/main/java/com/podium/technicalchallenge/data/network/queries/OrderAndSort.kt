package com.podium.technicalchallenge.data.network.queries

enum class OrderBy(val field: String) {
    POPULARITY("popularity"),
    TITLE("title"),
    VOTE_AVERAGE("voteAverage")
}

enum class Sort {
    ASC,
    DESC
}