package com.milione.model.response

data class FavouriteSportResponse(
    val label_name: String,
    val sports_array: ArrayList<SportsArray>,
    val status: Int,
    val token: Any
) {
    data class SportsArray(
        val grey_logo: String,
        val id: String,
        val language_id: Int,
        val light_logo: String,
        val position: String,
        val status: String,
        val title: String
    )
}