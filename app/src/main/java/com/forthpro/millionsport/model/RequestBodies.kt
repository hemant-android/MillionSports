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
}