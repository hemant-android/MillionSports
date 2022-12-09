package com.forthpro.millionsport.model.response

data class GetAllLanguageResponse(
    val language: ArrayList<Language>,
    val defaultLanguage: String,
    val status: Int,
    val token: Any,
) {
    data class Language(
        val created_at: String,
        val id: String,
        val language_logo: String,
        val name: String,
        val status: String,
        val updated_at: String,
        var isSelect: Boolean = false,
    )
}