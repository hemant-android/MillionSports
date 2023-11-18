package com.milione.model.response

data class MatchResponse(
    val COMPETITIONS_LABEL: String,
    val MATCH_LABEL: String,
    val TEAMS_LABEL: String,
    val matches: ArrayList<Matche>,
    val message: String,
    val status: Int,
    val token: Any,
) {

    data class Matche(
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
        val country: Country,
        val country_id: Int,
        val prediction_date: String,
        val prediction_name: String,
        val prediction_time: String,
        val sport_id: Int
    ) {
        data class Country(
            val country_logo: String,
            val created_at: String,
            val id: Int,
            val name: String,
            val status: String,
            val updated_at: String,
        )
    }
}