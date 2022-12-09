package com.forthpro.millionsport.model.response


data class CommonResponse(
    val showText: String,
    val status: Int,
    val token: Any,
    val message: String,
)