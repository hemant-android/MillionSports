package com.forthpro.millionsport.model.response

data class DashboardResponse(
    val POPULAR_COMPETITIONS_BY_COUNTRY_LABEL: String,
    val POPULAR_COMPETITIONS_LABEL: String,
    val dateArray: ArrayList<DateArray>,
    val popular_competitions: ArrayList<PopularCompetition>,
    val popular_competitions_country: ArrayList<PopularCompetitionsCountry>,
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

    data class PopularCompetition(
        val country_id: Int,
        val country_logo: String,
        val country_name: String,
        val default_name: String,
        val language_id: Int,
        val name: String,
        val prediction: List<Prediction>,
        val sport_id: Int
    ) {
        data class Prediction(
            val Away: String,
            val FT_1: String,
            val FT_2: String,
            val FT_X: String,
            val FT_2W_1: String,
            val FT_2W_2: String,
            val Player_H_1: String,
            val Player_H_2: String,
            val Player_A_1: String,
            val Player_A_2: String,
            val Home: String,
            val id: String,
            val prediction_date: String,
            val prediction_name: String,
            val prediction_time: String
        )
    }

    data class PopularCompetitionsCountry(
        val country_id: Int,
        val country_logo: String,
        val country_name: String,
        val default_name: String,
        val language_id: Int,
        val name: String,
        val prediction: List<Prediction>,
        val sport_id: Int
    ) {
        data class Prediction(
            val Away: String,
            val FT_1: String,
            val FT_2: String,
            val FT_X: String,
            val FT_2W_1: String,
            val FT_2W_2: String,
            val Player_H_1: String,
            val Player_H_2: String,
            val Player_A_1: String,
            val Player_A_2: String,
            val Home: String,
            val id: String,
            val prediction_date: String,
            val prediction_name: String,
            val prediction_time: String
        )
    }

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