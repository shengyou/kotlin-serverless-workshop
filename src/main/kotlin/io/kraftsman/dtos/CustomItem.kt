package io.kraftsman.dtos

import tw.ktrssreader.annotation.RssRawData
import tw.ktrssreader.annotation.RssTag

@RssTag(name = "item")
data class CustomItem(
    val title: String?,
    @RssRawData(rawTags = ["dc:creator"])
    val author: String?,
    val description: String?,
    val link: String?,
    val pubDate: String?,
)
