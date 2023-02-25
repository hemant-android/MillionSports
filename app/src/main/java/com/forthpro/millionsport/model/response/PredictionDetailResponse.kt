package com.forthpro.millionsport.model.response

data class PredictionDetailResponse(
    val predictionTab: ArrayList<PredictionTab>,
    val status: Int,
    val token: Any
) {
    data class PredictionTab(
        val label_array: ArrayList<LabelArray>,
        val label_name: String
    ) {
        data class LabelArray(
            val label1_array: ArrayList<Label1Array>,
            val label_name: String
        ) {
            data class Label1Array(
                val label_value: String
            )
        }
    }
}