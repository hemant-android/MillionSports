package com.forthpro.millionsport.model.response

data class FavMatchesResponse(
    val COMPETITIONS_LABEL: String,
    val MATCH_LABEL: String,
    val TEAMS_LABEL: String,
    val dateArray: ArrayList<DateArray>,
    val matches: ArrayList<Matche>,
    val message: String,
    val sports: ArrayList<Sport>,
    val status: Int,
    val token: Any
) {
    data class DateArray(
        val date_combine: String,
        val date_value: String,
        val date_value1: String,
        val day_name: String,
        var isSelect: Boolean = false
    )

    data class Matche(
        val Away: String,
        val FT_1: String,
        val FT_2: String,
        val FT_X: String,
        val Home: String,
        val id: String,
        val prediction_date: String,
        val prediction_name: String,
        val prediction_time: String
    )

    data class Sport(
        val grey_logo: String,
        val id: String,
        val language_id: String,
        val light_logo: String,
        val sportsCount: String,
        val status: String,
        val title: String,
        var isSelect: Boolean = false
    )
}