package com.forthpro.millionsport.model.response

import java.io.Serializable

data class SideMenuResponse(
    val sideBar: ArrayList<SideBar>,
    val status: Int,
    val token: Any,
) : Serializable {
    data class SideBar(
        val label_array: ArrayList<LabelArray>,
        val label_name: String,
        val uniqueKey: String,
    ) : Serializable {
        data class LabelArray(
            val label_key: String,
            val label_value: String,
        ) : Serializable
    }
}