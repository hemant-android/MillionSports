package com.milione.model.response

data class TeamResponse(
    val ADDTEAMS_LABEL: String,
    val COMPETITIONS_LABEL: String,
    val MATCH_LABEL: String,
    val TEAMS_LABEL: String,
    val favTeams: ArrayList<FavTeam>,
    val message: String,
    val status: Int,
    val token: Any,
    val NO_LABEL: String,
    val REMOVE_LABEL: String,
    val YES_LABEL: String,
) {
    data class FavTeam(
        val country: Country,
        val country_id: Int,
        val created_at: String,
        val device_id: String,
        val favourite: Int,
        val id: Int,
        val sport_id: Int,
        val team_name: String,
        val updated_at: String,
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