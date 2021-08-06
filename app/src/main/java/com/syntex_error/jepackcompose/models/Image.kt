package com.syntex_error.jepackcompose.models
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val height: Int? =0 ,
    val id: String?="",
    val url: String?="",
    val width: Int?=0
)