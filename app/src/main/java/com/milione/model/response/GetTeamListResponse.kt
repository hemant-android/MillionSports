package com.milione.model.response

data class GetTeamListResponse(
    val ADDTEAM_LABEL: String,
    val NO_LABEL: String,
    val POPULAR_TEAMS_LABEL: String,
    val REMOVE_LABEL: String,
    val SEARCH_LABEL: String,
    val YES_LABEL: String,
    val allTeams: ArrayList<AllTeam>,
    val status: Int,
    val token: Any
) {
    data class AllTeam(
        val country: Country,
        val country_id: Int,
        val created_at: String,
        var favourite: Int,
        val id: Int,
        val sport_id: Int,
        val team_name: String,
        val updated_at: String
    ) {
        data class Country(
            val country_logo: String,
            val created_at: String,
            val id: Int,
            val name: String,
            val status: String,
            val updated_at: String
        )
    }
}