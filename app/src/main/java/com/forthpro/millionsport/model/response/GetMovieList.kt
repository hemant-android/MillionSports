package com.forthpro.millionsport.model.response

data class GetMovieList(
    val page: Int,
    val results: ArrayList<Result>,
    val total_pages: String,
    val total_results: String
) {
    data class Result(
        val adult: Boolean,
        val backdrop_path: String,
        val genre_ids: List<Int>,
        val id: String,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: String
    )
}