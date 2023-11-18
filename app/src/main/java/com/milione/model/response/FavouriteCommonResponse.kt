package com.milione.model.response

data class FavouriteCommonResponse(
    val COMPETITIONS_LABEL: String,
    val MATCH_LABEL: String,
    val TEAMS_LABEL: String,
    val dateArray: ArrayList<DateArray>,
    val message: String,
    val sports: ArrayList<Sport>,
    val status: Int,
    val token: Any,
) {
    data class DateArray(
        val date_combine: String,
        val date_value: String,
        val date_value1: String,
        val day_name: String,
        var isSelect: Boolean = false,
    )

    data class Sport(
        val grey_logo: String,
        val id: String,
        val language_id: String,
        val light_logo: String,
        val sportsCount: String,
        val status: String,
        val title: String,
        var isSelect: Boolean = false,
    )
}