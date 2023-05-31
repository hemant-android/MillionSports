package com.forthpro.millionsport.model.response

data class GetCompetitionListResponse(
    val NO_LABEL: String,
    val POPULAR_LABEL: String,
    val REMOVE_LABEL: String,
    val SEARCH_LABEL: String,
    val YES_LABEL: String,
    val allCompetiontion: ArrayList<AllCompetiontion>,
    val status: Int,
    val token: Any
) {
    data class AllCompetiontion(
        val country: Country,
        val country_id: Int,
        val created_at: String,
        val default_name: String,
        var favourite: Int,
        val id: Int,
        val language_id: Int,
        val name: String,
        val sport_id: Int,
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