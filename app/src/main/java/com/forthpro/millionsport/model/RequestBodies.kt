package com.forthpro.millionsport.model

object RequestBodies {

    data class LoginBody(
        val plate_no: String,
        val lat: String,
        val lng: String,
    )
}