package io.buildwithnd.demotmdb.model

data class MovieSearchDTO(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)