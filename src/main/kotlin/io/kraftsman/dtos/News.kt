package io.kraftsman.dtos

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class News(
    val id: Int?,
    val title: String,
    val author: String,
    val content: String,
    val permalink: String,
    @Serializable(with = LocalDateTimeAsStringSerializer::class)
    val publishedAt: LocalDateTime,
)
