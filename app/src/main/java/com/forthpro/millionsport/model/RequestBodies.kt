package com.forthpro.millionsport.model

object RequestBodies {

    data class LanguageLabelBody(
        val languagetext: String,
        val languageId: String,
    )
}