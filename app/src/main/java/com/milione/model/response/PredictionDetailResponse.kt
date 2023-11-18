package com.milione.model.response

data class PredictionDetailResponse(
    val awayTeam: String,
    val Player_H_1: String,
    val Player_H_2: String,
    val Player_A_1: String,
    val Player_A_2: String,
    val country_logo: String,
    val country_id: String,
    val country_name: String,
    val grey_logo: String,
    val sets: String,
    val player: String,
    val homeTeam: String,
    val light_logo: String,
    val predictionTab: ArrayList<PredictionTab>,
    val prediction_date: String,
    val prediction_name: String,
    val prediction_time: String,
    val status: Int,
    val homeFavourite: String,
    val awayFavourite: String,
    val token: Any,
) {
    data class PredictionTab(
        val label_array: ArrayList<LabelArray>,
        val label_name: String,
        var isSelect: Boolean = false,
    ) {
        data class LabelArray(
            val label1_array: ArrayList<Label1Array>,
            val label_name: String,
        ) {
            data class Label1Array(
                val label_key: String,
                val label_key1: String,
                val label_key2: String,
                val label_key3: String,
                val label_key4: String,
                val label_key5: String,
                val label_key6: String,
                val label_value: String,
                val label_value1: String,
                val label_value2: String,
                val label_value3: String,
                val label_value4: String,
                val label_value5: String,
                val label_value6: String,
                val label_key_up: String,
                val label_key_down: String,
                val name: String,
            )
        }
    }
}