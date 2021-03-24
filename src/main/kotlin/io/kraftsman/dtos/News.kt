package io.kraftsman.dtos

import kotlinx.serialization.Serializable

@Serializable
data class News(
    val id: Int?,
    val title: String,
    val author: String,
    val content: String,
    val permalink: String,
    val publishedAt: String,
)
