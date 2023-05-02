package com.forthpro.millionsport.model.response

data class SideMenuResponse(
    val sideBar: ArrayList<SideBar>,
    val status: Int,
    val token: Any
) {
    data class SideBar(
        val label_array: List<LabelArray>,
        val label_name: String,
        val uniqueKey: String
    ) {
        data class LabelArray(
            val label_key: String,
            val label_value: String
        )
    }
}