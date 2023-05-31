package com.forthpro.millionsport.model.response

data class NotificationResponse(
    val label_name: String,
    val notification_array: ArrayList<NotificationArray>,
    val status: Int,
    val token: Any
) {
    data class NotificationArray(
        val id: Int,
        val label_value: String,
        var notification: Int,
        val unique_key: String,
        var isSelect: Boolean = false
    )
}