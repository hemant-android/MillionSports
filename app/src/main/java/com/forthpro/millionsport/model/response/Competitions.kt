package com.forthpro.millionsport.model.response

data class Competitions(
    val ADD_COMPETITIONS_LABEL: String,
    val COMPETITIONS_LABEL: String,
    val MATCH_LABEL: String,
    val TEAMS_LABEL: String,
    val competitions: ArrayList<Competition>,
    val message: String,
    val status: Int,
    val token: Any
) {
    data class Competition(
        val country_id: Int,
        val country_logo: String,
        val country_name: String,
        val default_name: String,
        val favourite: Int,
        val language_id: Int,
        val name: String,
        val sport_id: Int
    )
}