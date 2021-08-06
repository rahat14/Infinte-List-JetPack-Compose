package com.syntex_error.jepackcompose.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DogModel(
    val bred_for: String? = "",
    val id: Int? = 0,
    val image: Image? = Image(),
    val life_span: String? ="",
    val breed_group: String? ="",
    val name: String? ="" ,
    val origin: String? ="",
    val reference_image_id: String? ="" ,
    val temperament: String?=""
)