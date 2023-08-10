package com.forthpro.millionsport.model

object RequestBodies {

    data class LanguageLabelBody(
        val languagetext: String,
        val languageId: String,
    )

    data class ChangeLanguageBody(
        val platform: String,
        val device_id: String,
        val device_token: String,
        val userTimeZone: String,
        val userTimeZone1: String,
    )

    data class DashboardBody(
        val device_id: String,
        val sportId: String,
        val chooseDate: String,
    )

    data class PredictionDetailsBody(
        val device_id: String,
        val sportId: String,
        val predictionId: String,
    )

    data class FavBody(
        val device_id: String,
        val sportId: String,
        val chooseDate: String,
    )

    data class GetTeamListBody(
        val device_id: String,
        val sportId: String,
    )

    data class GetNotificationBody(
        val device_id: String,
    )

    data class UpdatedNotificationBody(
        val device_id: String,
        val notification_id: String,
        val notification: String,
    )

    data class FavAddRemoveBody(
        val device_id: String,
        val sportId: String,
        val country_id: String,
        val team_name: String,
        val favourite: String,
    )

    data class FavAddRemoveCompetitionBody(
        val device_id: String,
        val sportId: String,
        val country_id: String,
        val default_name: String,
        val favourite: String,
    )

    data class UpdatedSportItemBody(
        val device_id: String,
        val fromSportId: String,
        val fromposition: String,
        val toSportId: String,
        val toposition: String,
    )
}