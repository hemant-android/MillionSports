package com.forthpro.millionsport.model

object RequestBodies {

    data class LanguageLabelBody(
        val languagetext: String,
        val languageId: String,
    )

    data class DashboardBody(
        val sportId: String,
        val chooseDate: String,
    )

    data class PredictionDetailsBody(
        val sportId: String,
        val predictionId: String,
    )

    data class FavBody(
        val device_id: String,
        val sportId: String,
        val chooseDate: String,
    )
}